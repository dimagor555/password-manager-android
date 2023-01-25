package ru.dimagor555.password.data

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import kotlin.reflect.KProperty

inline fun <reified T : RealmObject> Realm.getById(id: String, name: String = "id"): T =
    this.query<T>("$name == uuid($id)").map()

suspend inline fun <reified T : RealmObject> Realm.addOrUpdate(model: T): T =
    this.write {
        copyToRealm(model)
    }

suspend inline fun <reified T : RealmObject> Realm.removeById(id: String, name: String = "id") {
    this.write {
        val model = this@removeById.getById<T>(id, name)
        delete(model)
    }
}

fun <T : RealmObject> RealmQuery<T>.map() = this.find().first()

infix fun KProperty<*>.eqId(value: Any): String =
    "$name == uuid($value)"

fun getUuid(id: String?): RealmUUID =
    if (id == null) RealmUUID.random() else RealmUUID.from(id)

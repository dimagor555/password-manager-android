package ru.dimagor555.password.data

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.first
import kotlin.reflect.KProperty

// TODO make suspend and refactor, it runs on main thread
inline fun <reified T : RealmObject> Realm.getById(id: String, name: String = "id"): T =
    this.query<T>("$name == uuid($id)").map()

suspend inline fun <reified T : RealmObject> Realm.queryOneOrNull(
    query: String,
): T? =
    query<T>(query)
        .first()
        .asFlow()
        .first()
        .obj

suspend inline fun <reified T : RealmObject> Realm.add(model: T): T =
    this.write {
        copyToRealm(model)
    }

suspend inline fun <reified T : RealmObject> Realm.removeById(id: String, name: String = "id") {
    this.write {
        val model = this.query<T>("$name = uuid($id)").find().first()
        delete(model)
    }
}

// TODO remove because request to DB on main thread
fun <T : RealmObject> RealmQuery<T>.map(): T = this.find().first()

infix fun KProperty<*>.eqId(value: Any): String =
    "$name == uuid($value)"

fun getUuid(id: String?): RealmUUID =
    if (id == null) RealmUUID.random() else RealmUUID.from(id)
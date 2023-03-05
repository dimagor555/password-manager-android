package ru.dimagor555.password.data

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import kotlinx.datetime.Instant
import kotlin.reflect.KProperty

inline fun <reified T : RealmObject> Realm.getById(id: String, name: String = "id"): T =
    this.query<T>("$name == uuid($id)").map()

suspend inline fun <reified T : RealmObject> Realm.addOrUpdate(model: T): T =
    this.write {
        copyToRealm(model)
    }

suspend inline fun <reified T : RealmObject> Realm.removeById(id: String, name: String = "id") {
    this.write {
        val model = this.query<T>("$name = uuid($id)").find().first()
        delete(model)
    }
}

fun <T : RealmObject> RealmQuery<T>.map(): T = this.find().first()

infix fun KProperty<*>.eqId(value: Any): String =
    "$name == uuid($value)"

fun getUuid(id: String?): RealmUUID =
    if (id == null) RealmUUID.random() else RealmUUID.from(id)

fun RealmInstant.toInstant(): Instant {
    val sec: Long = this.epochSeconds
    val nano: Int = this.nanosecondsOfSecond
    return if (sec >= 0) {
        Instant.fromEpochSeconds(sec, nano.toLong())
    } else {
        Instant.fromEpochSeconds(sec - 1, 1_000_000 + nano.toLong())
    }
}

fun Instant.toRealmInstant(): RealmInstant {
    val sec: Long = this.epochSeconds
    val nano: Int = this.nanosecondsOfSecond
    return if (sec >= 0) {
        RealmInstant.from(sec, nano)
    } else {
        RealmInstant.from(sec + 1, -1_000_000 + nano)
    }
}

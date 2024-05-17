package com.houvven.guise.hook.profile

import android.os.Parcelable
import com.houvven.guise.hook.profile.item.AppProfile
import com.houvven.guise.hook.profile.item.PropertiesProfile
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json


@Serializable
@Parcelize
data class ModuleHookProfiles(
    @Transient
    val packageName: String? = null,
    val properties: PropertiesProfile = PropertiesProfile.Empty,
    val app: AppProfile = AppProfile.Empty
) : HookProfile, Parcelable {

    override val isEffective: Boolean
        get() = this != Empty.copy(packageName = this.packageName)

    fun toJsonStr(): String {
        return Json.encodeToString(serializer(), this)
    }

    companion object {
        private const val TAG = "ModuleHookProfiles"

        @JvmStatic
        val Empty = ModuleHookProfiles()

        @JvmStatic
        val Debug = ModuleHookProfiles(
            properties = PropertiesProfile(
                brand = "Xiaomi",
                model = "M2105K81C",
                characteristics = "tablet"
            )
        )

        fun fromJsonStr(json: String): ModuleHookProfiles {
            return runCatching { Json.decodeFromString(serializer(), json) }.getOrDefault(Empty)
        }

    }
}
package com.example.pokemon_daws.pokemon

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pokemon_daws.Controllers.ApiController
import com.example.pokemon_daws.Controllers.TypeRelation
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TypeSingleton(
    val lifecycleScope: LifecycleCoroutineScope
){
    private var api = ApiController(lifecycleScope)
    private var ls = lifecycleScope
    private lateinit var allTypeRelations: MutableMap<String, TypeRelation>
    private lateinit var types: List<String>

    companion object {
        @Volatile
        private var INSTANCE: TypeSingleton? = null

        fun getTypeSingleton(inpLifecycleScope: LifecycleCoroutineScope): TypeSingleton? {
            return INSTANCE?: synchronized(this) {
//                val instance = TypeSingleton(inpLifecycleScope)
//
//
//
//                instance.allTypeRelations = mutableMapOf<String, TypeRelation>()
//
//                setTypeRelationsMap(instance)
//
//                println(instance.allTypeRelations)
//
//                INSTANCE = instance
//                instance

                INSTANCE = TypeSingleton(inpLifecycleScope)

                INSTANCE!!.allTypeRelations = mutableMapOf<String, TypeRelation>()

                setTypeRelationsMap()

                INSTANCE
            }
        }
        private fun setTypeRelationsMap() {
            INSTANCE?.ls?.launch(Dispatchers.IO){
                INSTANCE!!.types = INSTANCE!!.api.getTypes()

                for(type in INSTANCE!!.types) {
                    val typeRelations = INSTANCE!!.api.getTypeRelations(type)
                    if (typeRelations != null) {
                        INSTANCE!!.allTypeRelations.put(type, typeRelations)
                    }
                }

//                println(INSTANCE!!.allTypeRelations)
            }
        }
    }

    fun getEffectiveType(attackType: String, defenseType: String): TypeEffectiveness? {
        val typeRelations: TypeRelation? = allTypeRelations.get(attackType)

        var effectiveness = ""

        when(defenseType) {
            "normal" -> if (typeRelations != null) {
                effectiveness = typeRelations.normal
            }
            "fire" -> if (typeRelations != null) {
                effectiveness = typeRelations.fire
            }
            "water" -> if (typeRelations != null) {
                effectiveness = typeRelations.water
            }
            "electric" -> if (typeRelations != null) {
                effectiveness = typeRelations.electric
            }
            "grass" -> if (typeRelations != null) {
                effectiveness = typeRelations.grass
            }
            "ice" -> if (typeRelations != null) {
                effectiveness = typeRelations.ice
            }
            "fighting" -> if (typeRelations != null) {
                effectiveness = typeRelations.fighting
            }
            "poison" -> if (typeRelations != null) {
                effectiveness = typeRelations.poison
            }
            "ground" -> if (typeRelations != null) {
                effectiveness = typeRelations.ground
            }
            "flying" -> if (typeRelations != null) {
                effectiveness = typeRelations.flying
            }
            "psychic" -> if (typeRelations != null) {
                effectiveness = typeRelations.psychic
            }
            "bug" -> if (typeRelations != null) {
                effectiveness = typeRelations.bug
            }
            "rock" -> if (typeRelations != null) {
                effectiveness = typeRelations.rock
            }
            "ghost" -> if (typeRelations != null) {
                effectiveness = typeRelations.ghost
            }
            "dragon" -> if (typeRelations != null) {
                effectiveness = typeRelations.dragon
            }
        }

        return TypeEffectiveness.getTypeEffectiveness(effectiveness)
    }




//    suspend fun getEffectiveType(attackType: String, defenseType: String): TypeEffectiveness? {
//
//        var retVal: TypeEffectiveness? = null
//        ls.launch(Dispatchers.IO) {
//            val typeRelations = api.getTypeRelations(attackType)
//
//            var effectiveness = ""
//
//            when(defenseType) {
//                "normal" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.normal
//                }
//                "fire" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.fire
//                }
//                "water" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.water
//                }
//                "electric" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.electric
//                }
//                "grass" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.grass
//                }
//                "ice" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.ice
//                }
//                "fighting" -> if (typeRelations != null) {
//                effectiveness = typeRelations.fighting
//                }
//                "poison" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.poison
//                }
//                "ground" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.ground
//                }
//                "flying" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.flying
//                }
//                "psychic" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.psychic
//                }
//                "bug" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.bug
//                }
//                "rock" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.rock
//                }
//                "ghost" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.ghost
//                }
//                "dragon" -> if (typeRelations != null) {
//                    effectiveness = typeRelations.dragon
//                }
//            }
//
//            retVal = TypeEffectiveness.getTypeEffectiveness(effectiveness)
//        }.join()
//        return retVal
//    }
}

enum class TypeEffectiveness(damageRate: Double) {
    SUPER_EFFECTIVE(2.0),
    NOT_VERY_EFFECTIVE(0.5),
    NEUTRAL(1.0),
    NO_EFFECT(0.0);

    companion object {
        fun getTypeEffectiveness(effect: String?): TypeEffectiveness?{
            return when(effect){
                "super_effective" -> SUPER_EFFECTIVE
                "not_very_effective" -> NOT_VERY_EFFECTIVE
                "no_effect" -> NO_EFFECT
                null -> NEUTRAL
                else -> null
            }
        }
    }
}

enum class Type {
    @SerializedName("1") NORMAL,
    @SerializedName("2") FIRE,
    @SerializedName("3") WATER,
    @SerializedName("4") ELECTRIC,
    @SerializedName("5") GRASS,
    @SerializedName("6") ICE,
    @SerializedName("7") FIGHTING,
    @SerializedName("8") POISON,
    @SerializedName("9") GROUND,
    @SerializedName("10") FLYING,
    @SerializedName("11") PSYCHIC,
    @SerializedName("12") BUG,
    @SerializedName("13") ROCK,
    @SerializedName("14") GHOST,
    @SerializedName("15") DRAGON;
    companion object {
        fun getType(type: String): Type?{
            return when(type){
                "normal" -> Type.NORMAL
                "fighting" -> Type.FIGHTING
                "flying" -> Type.FLYING
                "poison" -> Type.POISON
                "ground" -> Type.GROUND
                "rock" -> Type.ROCK
                "bug" -> Type.BUG
                "ghost" -> Type.GHOST
                "fire" -> Type.FIRE
                "water" -> Type.WATER
                "grass" -> Type.GRASS
                "electric" -> Type.ELECTRIC
                "psychic" -> Type.PSYCHIC
                "ice" -> Type.ICE
                "dragon" -> Type.DRAGON
                else -> null
            }
        }
    }
}
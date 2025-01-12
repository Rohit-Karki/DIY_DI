val UNINITIALIZED = Any()
/*
    * A Factory knows how to create instances of a particular type.
    * It can leverage the ObjectGraph to retrieve the dependencies needed to create a collaborator.
*/

fun interface Factory<T> {
    fun get(objectGraph: ObjectGraph): T
}

interface Module {
    operator fun <T> get(requestedType: Class<T>): Factory<T>?
}

class FactoryHolderModule : Module {
    private val factories = mutableMapOf<Class<out Any?>, Factory<out Any?>>()

    override fun <T> get(requestedType: Class<T>): Factory<T>? = factories[requestedType] as Factory<T>?

    fun <T> install(requestedType: Class<T>, factory: Factory<T>) {
        factories[requestedType] = factory
    }
}

inline fun <reified T> FactoryHolderModule.install(
    noinline factory: ObjectGraph.() -> T,
) = install(T::class.java, factory)

inline fun <reified T> FactoryHolderModule.installSingleton(
    noinline factory: ObjectGraph.() -> T,
) = install(T::class.java, singleton(factory))

inline fun <reified REQUESTED, reified PROVIDED : REQUESTED>
    FactoryHolderModule.bind() {
    install(REQUESTED::class.java) { objectGraph ->
        objectGraph[PROVIDED::class.java]
    }
}

fun <T> singleton(factory: Factory<T>): Factory<T> {
    var instance: Any? = UNINITIALIZED

    return Factory{ linker ->
        if (instance == UNINITIALIZED) {
            instance = factory.get(linker)
        }
        instance as T
    }

}

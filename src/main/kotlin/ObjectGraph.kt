/*
    * ObjectGraph takes in a list of Module that knows how to create factories.
    * ObjectGraph.get() retrieves the factory from the modules and then calls Factory.get(ObjectGraph)
*/
class ObjectGraph(private val modules: List<Module>) {

    constructor(vararg modules: Module) : this(modules.asList())

    // Cache of factories already retrieves from modules.
    private val factoryHolder = FactoryHolderModule()

    operator fun <T> get(requestedType: Class<T>): T {
        val knownFactoryOrNull = factoryHolder[requestedType]
        val factory = knownFactoryOrNull ?: modules
            .firstNotNullOf { module -> module[requestedType] }
            .also { factory ->
                factoryHolder.install(requestedType, factory)
            }
        return factory.get(this)
    }
}

inline fun <reified T> ObjectGraph.get() = get(T::class.java)

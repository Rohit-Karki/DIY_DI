import java.lang.reflect.Constructor
import javax.inject.Inject

/*
   For a given class to inject, we use reflection to find the constructor annotated with @Inject

    val requestedType: Class<T> = //...
    val injectConstructor = requestedType.constructors.single {
        it.isAnnotationPresent(Inject::class.java)
    }


   We extract the types of the constructor parameters, ask the ObjectGraph for an instance of each
   parameter type then pass these parameters to the constructor:

    val objectGraph: ObjectGraph = // ...
    val parameters = injectConstructor.parameterTypes.map { paramType ->
      objectGraph[paramType]
    }.toTypedArray()
    val instance = injectConstructor.newInstance(*parameters)
*/

/*
    * A Factory knows how to create instances of a particular type.
    * It can leverage the ObjectGraph to retrieve the dependencies needed to create a collaborator.
*/
fun interface Factory<T> {
    fun get(objectGraph: ObjectGraph): T
}

class ReflectiveFactory<T>(
    requestedType: Class<T>,
) : Factory<T> {

    private val injectConstructor = requestedType.constructors.single{
        it.isAnnotationPresent(Inject::class.java)
    } as Constructor<T>
    override fun get(objectGraph: ObjectGraph): T {
        val parameters = injectConstructor.parameterTypes.map { paramType ->
            objectGraph[paramType]
        }.toTypedArray()

        return injectConstructor.newInstance(*parameters)
    }
}

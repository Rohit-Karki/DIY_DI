import animal.Cat
import animal.Dog
import animal.TopAnimal
import calculator.Calculator
import javax.inject.Inject
import javax.inject.Singleton

class CoffeeLogger

interface Heater

@Singleton
class ElectricHeater @Inject constructor(
    private val logger: CoffeeLogger,
) : Heater {
    // ...
}
interface Pump

class Thermosiphon(logger: CoffeeLogger, heater: Heater) : Pump

class CoffeeMaker(logger: CoffeeLogger, heater: Heater, pump: Pump) {
    fun brew() {
        println("Brewing")
    }
}

/*
fun main(args: Array<String>) {
    val module = FactoryHolderModule()
    module.bind<Heater, ElectricHeater>()
    module.bind<Pump, Thermosiphon>()
    module.installSingleton { CoffeeLogger() }
    module.install { ElectricHeater(get()) }

    module.install {
        Thermosiphon(get(), get())
    }
    module.install {
        CoffeeMaker(get(), get(), get())
    }
    val objectGraph = ObjectGraph(module)
    val coffeeMaker = objectGraph.get<CoffeeMaker>()

    coffeeMaker.brew()
    println("Hello World!")
}
*/

fun main(args: Array<String>) {
    val calc = Calculator()
    println(calc.add(1.2f, 2.3f))
    val dg: TopAnimal = Dog("Rohit", 22)
    dg.callAnimal()
    val ct: TopAnimal = Cat("RRK", 22)
    ct.callAnimal()
}

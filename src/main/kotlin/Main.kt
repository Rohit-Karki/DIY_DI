class CoffeeLogger

interface Heater

class ElectricHeater(logger: CoffeeLogger) : Heater

interface Pump

class Thermosiphon(logger: CoffeeLogger, heater: Heater) : Pump

class CoffeeMaker(logger: CoffeeLogger, heater: Heater, pump: Pump) {
    fun brew() {
        println("Brewing")
    }
}

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

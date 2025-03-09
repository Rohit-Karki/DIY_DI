package animal
abstract class TopAnimal(val name: String, val age: Int) {
    abstract fun callAnimal()
}

class Dog(name: String, age: Int) : TopAnimal(name, age) {
    override fun callAnimal() {
        println("$name is bhowing with age $age")
    }
}

class Cat(name: String, age: Int) : TopAnimal(name, age) {
    override fun callAnimal() {
        println("$name is meowing with age $age")
    }
}

package projet.gl51.store

class Product {
    String id
    String name
    String description
    double price
    double idealTemperature

    Product(String id, String name) {
        this.id = id
        this.name = name
    }

    Product(String name) {
        this.id = new Random().with {(1..6).collect {(('a'..'z')).join()[ nextInt((('a'..'z')).join().length())]}.join()}
        this.name = name
    }

    Product(String id, String name, String description, double price, double idealTemperature) {
        this.id = id
        this.name = name
        this.description = description
        this.price = price
        this.idealTemperature = idealTemperature
    }

    Product(String name, String description, double price, double idealTemperature) {
        this.id = new Random().with {(1..6).collect {(('a'..'z')).join()[ nextInt((('a'..'z')).join().length())]}.join()}
        this.name = name
        this.description = description
        this.price = price
        this.idealTemperature = idealTemperature
    }

}

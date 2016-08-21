package test

class Planet {
    static auditable = true

    String name
    String star
    Integer numOfSatellites

    static mapping = {
        name column: 'PLANET_NAME'
        star column: 'PARENT_STAR'
        numOfSatellites column: 'NUMBER_OF_MOONS'
    }
}

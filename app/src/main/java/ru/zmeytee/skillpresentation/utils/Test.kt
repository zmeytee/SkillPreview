package ru.zmeytee.skillpresentation.utils

import ru.zmeytee.skillpresentation.data.models.Address
import ru.zmeytee.skillpresentation.data.models.Company
import ru.zmeytee.skillpresentation.data.models.Geo
import ru.zmeytee.skillpresentation.data.models.User

object Test {

    val users = listOf(
        User.Local(
            id = 0,
            name = "name1",
            userName = "userName1",
            email = "email1",
            phone = "phone1",
            website = "website1"
        ),
        User.Local(
            id = 0,
            name = "name2",
            userName = "userName2",
            email = "email2",
            phone = "phone2",
            website = "website2"
        ),
        User.Local(
            id = 0,
            name = "name3",
            userName = "userName3",
            email = "email3",
            phone = "phone3",
            website = "website3"
        ),
    )

    val addresses = listOf(
        Address.Local(
            id = 0,
            userId = 1,
            street = "street1",
            suite = "suite1",
            city = "City1",
            zipcode = "zipcode1"
        ),
        Address.Local(
            id = 0,
            userId = 1,
            street = "street2",
            suite = "suite2",
            city = "City2",
            zipcode = "zipcode2"
        ),
        Address.Local(
            id = 0,
            userId = 1,
            street = "street3",
            suite = "suite3",
            city = "City3",
            zipcode = "zipcode3"
        ),
    )

    val geo = listOf(
        Geo.Local(
            id = 0,
            addressId = 1,
            lat = 13.3,
            lng = 31.1
        ),
        Geo.Local(
            id = 0,
            addressId = 1,
            lat = 13.3,
            lng = 31.1
        ),
        Geo.Local(
            id = 0,
            addressId = 1,
            lat = 13.3,
            lng = 31.1
        ),
    )

    val companies = listOf(
        Company.Local(
            id = 0,
            userId = 1,
            name = "Name1",
            catchPhrase = "Catch Phrase1",
            bs = "BS1"
        ),
        Company.Local(
            id = 0,
            userId = 1,
            name = "Name2",
            catchPhrase = "Catch Phrase2",
            bs = "BS2"
        ),
        Company.Local(
            id = 0,
            userId = 1,
            name = "Name3",
            catchPhrase = "Catch Phrase3",
            bs = "BS3"
        ),
    )
}
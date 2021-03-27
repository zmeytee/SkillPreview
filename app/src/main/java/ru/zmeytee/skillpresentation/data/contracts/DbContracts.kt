package ru.zmeytee.skillpresentation.data.contracts

object DbContracts {

    object User {
        const val TABLE_NAME = "users"
        const val ID = "id"
        const val NAME = "name"
        const val USER_NAME = "user_name"
        const val EMAIL = "email"
        const val ADDRESS = "address"
        const val PHONE = "phone"
        const val WEBSITE = "website"
        const val COMPANY = "company"
    }

    object Address {
        const val TABLE_NAME = "addresses"
        const val ID = "id"
        const val USER_ID = "user_id"
        const val STREET = "street"
        const val SUITE = "suite"
        const val CITY = "city"
        const val ZIP_CODE = "zip_code"
        const val GEO = "geo"
    }

    object Geo {
        const val TABLE_NAME = "geo"
        const val ID = "id"
        const val ADDRESS_ID = "address_id"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lng"
    }

    object Company {
        const val TABLE_NAME = "companies"
        const val ID = "id"
        const val USER_ID = "user_id"
        const val NAME = "name"
        const val CATCH_PHRASE = "catch_phrase"
        const val BS = "bs"
    }
}
package com.example.myapplication

class Person (
    var name: String,
    var email: String,
    var mobileNumber: String,
    var dayOfBirth: Int,
    var monthOfBirth: Int,
    var yearOfBirth: Int,
    var gender: String
){
    override fun toString(): String {
        return "Name: $name\nEmail: $email\nMobile Number: $mobileNumber\n" +
                "Date of Birth: $dayOfBirth/$monthOfBirth/$yearOfBirth\nGender: $gender"
    }
}
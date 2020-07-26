# Borja González Code Test
![Android Pull Request & Master CI](https://github.com/flaquir4/BorjaGonzalezCodeTest/workflows/Android%20Pull%20Request%20&%20Master%20CI/badge.svg)

In this repository you will find a simple login use case, where the user can put an email and a password to access a fake application.

The goal of this repository is to show what quality means to me.

## UI

It's created using material components, gesture navigation is enabled.

 ![Login Screen](./art/login_screen.png) 
 ![Login Screen](./art/main_screen.png) 
 
## Input validation

To check the user possible mistakes you will find an input validator. This validator has been made with demonstration rules rather than comprehensive ones. 
Those rules validate the emptiness of the fields and the proper format of them. 

In order to be allowed to login you should write any valid email and a password longer than 8 characters. 
 
## Quality validations

I think that the best validation checks are always the automatic ones.
- I've added [Detekt](https://detekt.github.io/detekt/) plugin, to perform static code analysis. 
- I've chosen [Ktlint](https://github.com/pinterest/ktlint) plugin for Detekt to enforce lint rules. 
- To check and fix the possible memory leaks I use [CanaryLeak](https://github.com/square/leakcanary).
- The repository also has a bunch of automated test to check our desired behaviour.
- To validate the test I've configured a [CI](https://github.com/flaquir4/BorjaGonzalezCodeTest/actions?query=workflow%3A%22Android+Pull+Request+%26+Master+CI%22) to run them on each pull request
 

## Architecture

The project uses an implementation of the clean architecture, this implementation uses three layer architecture with different patterns in each layer.
 
 -  PresentationLayer: MVP is used in this layer to reduce the responsibility of the view and to ease the testing.
 -  DomainLayer: In this layer we have our business rules.
 -  Data layer makes use of the Repository pattern to encapsulate the logic of each data source, it also improves a lot the readability of the data access.
    
I've chosen to use Dagger together with Hilt for dependency injection, it allows an easy graph dependency swap in tests.

At last, I have added a library called Result to deal with errors. I strongly believe that errors must be part of the return of the function, this allows the consumer to know all the possible outcomes that the functions may produce.  


## Test

This repository makes use of different test approaches.

  - Unit testing: is used to test the logic of the application, it's used in the Validator, Presenters and the Repository.
  - UI tests: these tests allows me to test the app is behaving as it is designed. The Repository is replaced with a fake one in order to test all the possible scenarios that can occur. This way it’s easy to check that the UI is behaving according to the expectations.
## Usage 

How to execute (you will need an emulator or a device with debug mode enabled)
````bash
./gradlew installDebug
````

Or you can find the latest apk [here](https://github.com/flaquir4/BorjaGonzalezCodeTest/actions) (select the first element in the list and you will see the binary)

In order to enter you must enter any valid email and a password longer than 8 characters.

How to test 
````bash
./gradlew check
./gradlew executeScreenshotTests
````

I've used an emulator with api 28 x64, and pixel_2_Xl format to take screenshots, you will need the same device in order to pass the tests.

License
-------

    Copyright 2020 Borja González

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
# String Mixer

## Description

[String Mixer](https://github.com/impedansa/string-mixer) is a backend project built using Java and Spring Boot. 
It provides an algorithm to visualise the differences between two input strings. 
It counts the frequency of lowercase letters (a to z) in each string, and then generates a result string based on specific criteria.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Build and Run](#build-and-run)
- [Usage](#usage)
  - [REST Interface](#rest-interface)
  - [Algorithm Details](#algorithm-details)
- [Project Structure and Implementation](#project-structure-and-implementation)
  - [Design Details](#design-details)
  - [Algorithm Implementation](#algorithm-implementation)
- [Testing](#testing)
- [Important Notes](#important-notes)
- [FAQ](#faq)
- [References](#references)

## Getting Started

### Prerequisites

- Java (version 19)
- Gradle (version 7.6 or higher)

### Build and Run

Clone the repository:

```bash
git clone https://github.com/impedansa/string-mixer.git
```

Build the project using Gradle:
```bash
./gradlew build
```

Run the project using Gradle:
```bash
./gradlew bootRun
```

## Usage

### REST Interface
The project includes a REST interface for using the string mixing algorithm. You can make a POST request to the following endpoint:

```
POST /api/strings/mix
```

Request should be structured like this:
```
{
    "first": "this is ssstringg1",
    "second": "this is ssssttring2"
}
```

Response looks like this:
```
{
    "result": "2:ssssss/2:ttt/=:iii/1:gg"
}
```

### Algorithm Details
The algorithm takes two input strings and counts the frequency of lowercase letters (a to z) in each string, considering only those with occurrences strictly greater than 1.
It then generates a result string based on specific criteria, considering the maximum occurrence of each eligible letter.
The result string contains all eligible lowercase letters appearing as many times as their maximum occurrence. 
These letters are prefixed by the number of the string where they appear with their maximum value and `:`. 
If the maximum is the same in both strings, the prefix is `=:`.
The different groups are separated by '`/`'. 
In the result string, substrings are sorted by length in descending order and alphabetically within the same length.

#### Sorting Criteria
The sorting of substrings in the result string is prioritised based on the following criteria:
1. **Length of Substrings**:
   * Substrings are sorted in descending order based on their length, determined by the number of occurrences of the letters.
2. **Prefix Order**:
   * An implicit order of `1:`, `2:`, and `=:`.
3. **Alphabetical Order**:
   * For substrings with the same length and the same prefix, alphabetical order is applied.

## Project Structure and Implementation
* **src/main/java/com/project/stringmixer/features/mix**: Contains the main source code for the string mixing algorithm.
  * **MixStringsController**: REST controller handling incoming requests.
  * **MixStringsService**: Service implementing the string mixing algorithm.
  * **CharacterOccurrenceMap**: Data structure representing the occurrence of each character in the strings.
* **src/main/java/com/project/stringmixer/features/mix/dto**: Contains DTOs (Data Transfer Objects) used for request and response.
* **src/test/java/com/project/stringmixer/features/mix**: Contains test classes for the algorithm and controller.

### Design Details
This project follows the Vertical Slice Architecture, an architectural pattern that organises code around features or functionalities rather than traditional layered architecture. 
In the context of String Mixer, each feature or use case is encapsulated within its own vertical slice. 
This approach promotes a more modular and maintainable codebase, making it easier to add or modify features without affecting other parts of the system.
The use of vertical slices enhances the scalability and testability of the codebase, facilitating a more streamlined development process.
More information about this architectural pattern can be found [here](https://garywoodfine.com/implementing-vertical-slice-architecture/).

### Algorithm Implementation
Step by step algorithm implementation and execution:
1. **Input Strings and Character Occurrence Counting**
   * The algorithm can process multiple strings, but we will consider the example where it takes two input strings, `s1` and `s2`. 
   It then iterates through each character in both strings, counting the occurrences of lowercase letters (a to z) and storing this information in a `CharacterOccurrenceMap`. 
   This structure keeps track of the maximum occurrence of each character in both strings, as well as the string ID (`1` or `2`) where the maximum occurrence is found.

2. **Sorting Criteria Initialisation**
   * The `CharacterOccurrenceMap` class initialises sorting-related variables such as `maxOccurrence`, `maxStringId`, and `allMaxesEqual`. 
   These variables will be used to determine the sorting order of the result string.

3. **Incrementing Character Count**
   * For each lowercase letter in the input strings, the algorithm increments the character count in the corresponding `CharacterOccurrenceMap`. 
   This step ensures that the algorithm captures the maximum occurrences of each character across both strings.

4. **Find Maximum Occurrences**
   * After counting occurrences, the algorithm determines the maximum occurrence of each character and the corresponding string ID where the maximum occurs. 
   This information is crucial for constructing the result string.

5. **Check for All Maxes Equal**
   * The algorithm checks whether all maximum occurrences across different strings are equal. 
   If they are equal, the `allMaxesEqual` flag is set to true. 
   This is important for determining the prefix of the result string.

6. **Generate Result String**
   * The `toString()` method in the `CharacterOccurrenceMap` class constructs the result string based on the previously calculated maximum occurrences, string IDs, and the `allMaxesEqual` flag. 
   The result string includes the appropriate prefix (`1:`, `2:`, or `=:`) and repeats the character according to its maximum occurrence.

7. **Sorting Result String**
   * The final result string is sorted based on the specified criteria. 
   First, it is sorted by length in descending order. 
   Then, substrings with the same length are sorted alphabetically.

8. **Collecting Result**
   * The sorted substrings are collected and joined with '`/`' to form the final result string. 
   This string is returned as the output of the algorithm.

## Testing
The project includes unit and integration tests to ensure the correctness of the string mixing algorithm. 
You can run the tests using the following Gradle command:

```bash
./gradlew test
```

#### Postman Testing
For additional testing, Postman collections are available in the `doc` folder. 
You can import the provided Postman collection and environment to easily perform API testing:

* Postman Collection: [doc/StringMixer.postman_collection.json](https://github.com/impedansa/string-mixer/blob/main/doc/Mix%20Strings.postman_collection.json)

* Postman Environment: [doc/StringMixer.postman_environment.json](https://github.com/impedansa/string-mixer/blob/main/doc/Mix%20Strings.postman_environment.json)

These resources include a pre-configured request to the `/api/strings/mix` endpoint, allowing you to test the string mixing algorithm with different input strings and verify the expected results.

To run the Postman tests:
* Import the Postman collection and environment into your Postman application. 
* Set the environment variable (the base URL). 
* Execute the request in the collection to interact with the string mixing API.

This testing approach provides an additional layer of validation and ensures that the API behaves as expected in a real-world scenario.

## Important Notes

This solution is intentionally designed to be extensible and accommodate the processing of multiple strings with minimal modification.
The architecture is structured to accommodate future enhancements by abstracting the string mixing logic into a service that can easily be extended.
The algorithm can seamlessly handle additional input strings without requiring substantial changes to the core logic. 
To extend the functionality for more input strings, primarily the request DTO needs modification to include the new strings, and the corresponding changes should be made in the controller to accommodate the additional parameters.
The vertical slice architecture also facilitates the modular organisation of features, making it straightforward to add new functionalities without impacting existing components.
This deliberate over-engineering aims to provide a scalable and flexible solution for any future enhancements.

## FAQ

1. **Why is the POST method used for the string mix calculation instead of the GET method?**

    The decision to use the POST method for this calculation is rooted in considerations of practicality and potential limitations. 
While idempotence is a key characteristic of GET requests, making them ideal for operations that don't cause side effects, the choice to use POST in this case is influenced by the potential variability in the length of string parameters.
Since there isn't an explicit maximum length defined for the input strings, using POST allows for the flexibility to handle larger amounts of data without being constrained by URL length limitations. 
POST requests allow for the inclusion of a request body, making it suitable for transmitting data, such as the input strings, in a more robust and scalable manner.
While GET requests are typically reserved for safe and idempotent operations, the decision to use POST in this context prioritises practicality and accommodates potential variations in the size of the input data, ensuring a robust and scalable solution.

2. **Why is vertical slice architecture used instead of layered architecture?**

    The adoption of vertical slice architecture over the traditional layered architecture is driven by an emphasis on extendability and scalability. 
Vertical slice architecture organises the codebase around features or functionalities rather than layers, providing a modular structure that aligns with the project's goal of easily accommodating new features. 
This design choice ensures that the addition of functionality is contained within its own slice, minimizing the impact on existing components. 
The result is a more flexible and maintainable codebase, allowing for seamless integration of new features without disrupting the overall system architecture.

3. **Why are unit and integration tests in the same package?**
    
    The decision to place unit and integration tests in the same package is influenced by the adoption of vertical slice architecture. 
In a vertical slice architecture, tests for a specific feature or functionality are often grouped together, irrespective of whether they are unit or integration tests. 
This organisation aligns with the modular structure of the codebase, making it easier to locate and manage tests related to a particular vertical slice. 
It emphasises a cohesive testing strategy that revolves around the features being tested, contributing to a more intuitive and maintainable testing infrastructure.

4. **Why are there both `@WebMvcTest` and `@SpringBootTest` tests for the controller?**
   
    The dual use of `@WebMvcTest` and `@SpringBootTest` for the controller serves distinct testing purposes.
`@WebMvcTest` is employed to isolate and specifically test the web layer of the controller, ensuring that the controller's behavior, request mappings, and interactions with the MVC components are correctly configured. 
On the other hand, `@SpringBootTest` provides a more comprehensive integration test that loads the entire Spring application context, offering a holistic assessment of the controller within the broader application context. 
This dual-testing approach allows for both focused unit testing of the web layer and more comprehensive integration testing of the controller within the complete application context.

5. **Why weren't parametrized tests used?**
   
    Parametrized tests are generally beneficial when we want to run the same test logic with different sets of input parameters. 
In the `MixStringsControllerTest` and `MixStringsControllerIT` classes, several test cases cover various scenarios, including valid parameters, null parameters, empty parameters, and invalid JSON formats. 
While parametrized tests can be useful for systematically exploring a broader range of inputs, tests in this project are quite clear and straightforward. 
Each test case tests a specific scenario, and introducing parameterization might not necessarily improve readability or maintainability in this specific case.
In situations where we have a larger number of similar test cases or want to explore a more systematic range of inputs, parametrized tests can be beneficial. 
However, for the current set of tests, the existing approach appears reasonable. 
It's crucial to prioritise clarity and readability in the code, and only introduce complexity when it brings clear benefits.

6. **Why wasn't an interface used for the service class?**

   The decision to not use an interface for the service class is rooted in simplicity and practicality. 
In scenarios where a class has only one method, and that method is specific to the context of the class, introducing an interface might not add significant value. 
The service class in this case has a single responsibility, and its method is tailored for the string mixing logic. 
Creating an interface for this class might introduce unnecessary abstraction and complexity without providing tangible benefits. 
Interfaces are powerful when we anticipate multiple implementations or want to enforce a contract across multiple classes. 
In situations where a class has a clear and singular responsibility, opting for a straightforward implementation without an interface often results in cleaner and more readable code.

## References

* https://garywoodfine.com/implementing-vertical-slice-architecture/
* https://www.milanjovanovic.tech/blog/vertical-slice-architecture
* https://www.jimmybogard.com/vertical-slice-architecture/
* https://www.guru99.com/difference-get-post-http.html
* https://symflower.com/en/company/blog/2023/best-practices-for-spring-boot-testing/
* https://www.appsdeveloperblog.com/difference-between-springboottest-and-webmvctest/
* https://reflectoring.io/spring-boot-web-controller-test/

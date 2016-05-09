# RxAndroidSamples
My test bed for RxAndroid examples

This is a sample project to demonstrate the greatness of RxAndroid and the general use of it. In particular, this repo has
samples related to the following topics

 - Use of observables, observer, subscription
 - RxJava / RxAndroid's operators
   - map
   - debounce
   - from
   - create
   - merge
   - defer
 - Okhttp
 - Retrolambda
 - Robolectric unit tests

# Media playback example

This example is to showcase how normal media components like player and clock can be converted to player observable and clock obervable, their interoperability and how UI can be updated based on the values emitted by them. Also shows how clock obervable is used as a cold observable. The following operators / functionalities have been added to the project

 - interval
 - publish / connect

# How to use Docker in Maven integration tests.
This example shows how you can use Docker in Maven integration tests.

## Problem
Often times when you want to test an interaction between an external service and your code you need to have the service installed somewhere. It can be your computer or a shared server. With a local service you are the only one who can run the tests. With shared service there is an upkeep overhead, you have to make sure that nobody can modify the service. Also to add a new service/test data you have to go trough a process established in your company. This makes writing integration tests a rather time consuming task.

## Solution
When using Docker you can run your service in a consistent way on any machine. This allows you to create and share a test environment quickly. This means the tests can be executed anywhere. On your coworker's or build machine. 

## Implementation
I use:
 - [io.fabric8:docker-maven-plugin](https://github.com/fabric8io/docker-maven-plugin) to run Docker containers
 - `port-allocator-maven-plugin` to find random unused ports for services
 - `exec-maven-plugin` to create a VM with `vagrant` for builds done where docker is not available natively (Windows, Mac)

# Prerequisites
For a Linux machine you'll need to have [docker engine](https://docs.docker.com/engine/installation/) installed. For anything else you'll need to install [VirtualBox](https://www.virtualbox.org/wiki/Downloads) and [Vagrant](https://www.vagrantup.com/downloads.html).

# How to run tests
    $ mvn clean verify

During the build Maven will:
 1. use vagrant to create a VM if docker engine is not supported natively (this step is skipped on Linux)
 2. find random unused ports on the host machine for each test
 3. run a docker container for each test with previously allocated ports bound
 4. run the tests against the services in containers
 5. destroy containers
 6. destroy the VM (skipped on Linux)

When the build is executed first time it will download VM/images. Subsequent executions take a lot less time.

# How to debug tests
## Full maven build debugging
    mvn -Dmaven.failsafe.debug clean verify
This will make Maven pause the build right before the tests until you establish a debugging session on port `5005`. More details can be found on [Maven Failsafe Plugin page](https://maven.apache.org/surefire/maven-failsafe-plugin/examples/debugging.html)
## Quick debugging
You can also run the containers yourself and debug the tests from you IDE as you would any simple unit tests. For example `MongoDbServiceStartupLogIT` test needs a mongodb service. Run

    $ docker run -p 27017:27017 mongo:3.2.6
 Then just run the test from your IDE as it if was a vanilla unit test.
 
# Troubleshooting
If you cannot start a VM [enable virtualization in your BIOS settings](http://askubuntu.com/a/256853).

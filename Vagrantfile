# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|

    config.vm.box = "minimum/ubuntu-trusty64-docker"
  
    config.vm.box_check_update = false

    # Create a private network, which allows host-only access to the machine using a specific IP.
    # make sure it's not in conflict with existing subnets
    config.vm.network "private_network", ip: "192.168.60.30"

    # make docker daemon listen on insecure 2375
    config.vm.provision "shell", inline: <<-SHELL
       # replace the whole file to avoid duplicated lines when provisioning multiple times
       sudo echo "DOCKER_OPTS='-H tcp://0.0.0.0:2375'" > /etc/default/docker
       sudo service docker restart
       sudo docker -H tcp://0.0.0.0:2375 version
    SHELL
end

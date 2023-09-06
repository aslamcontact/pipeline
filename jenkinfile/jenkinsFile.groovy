pipeline
        {
            agent {
                      docker {
                                image 'ubuntu:latest'
                                args ' -v /var/run/docker.sock:/var/run/docker.sock  -v /usr/bin/docker:/usr/bin/docker'
                            }
                    }
     options {
        skipDefaultCheckout()
    }
            stages{

                 stage('test') {
                     steps {

                         sh 'mkdir asm'
                         withCredentials([usernamePassword(credentialsId: 'docker_hub_access',
                                 usernameVariable: 'USERNAME',
                                 passwordVariable: 'PASSWORD')]
                         ) {

                             sh 'docker login --username $USERNAME --password $PASSWORD'
                         }


                     }
                 }

                stage('Front-end') {
                    agent {
                        docker { image 'node:18.17.1-alpine3.18' }
                    }
                    steps {
                        sh 'node --version'
                        sh 'ls'
                    }
                }




                 }




        }

def call(Map params = [:] ) {

    def args = [
           SLAVE_LABEL : "DOCKER"
    ]
    args << params

    pipeline {
      agent {
        node {
         label "${args.SLAVE_LABEL}"
        }
      }
    
        triggers {
          pollSCM('* * * * 1-5') 
        }
        environment {
            COMPONENT ="${args.COMPONENT}"
            PROJECT_NAME = "${args.PROJECT_NAME}"
            SLAVE_LABEL = "${args.SLAVE_LABEL}"
            APP_TYPE    = "${args.APP_TYPE}"
        }
        stages {
            stage('Build code & install dependencies') {
                steps {
                  sh '''
                    docker build -t local .
                  '''
                }

            }

        }
    }
}
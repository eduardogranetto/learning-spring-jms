### Commands

* `aws sqs create-queue --queue-name=test-queue`
* `seq 20 | xargs -Iz aws sqs send-message --queue-url=https://sqs.us-east-1.amazonaws.com/549516763280/test-queue --message-body="{}"`

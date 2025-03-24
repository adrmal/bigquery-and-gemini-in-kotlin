# Before first launch

Enter the `application.yml` file and set properties related to BigQuery and Gemini.

# How to request BigQuery

```bash
curl --request GET 'http://localhost:8080/bigQuery' \
--header 'Content-Type: application/json' \
--data '{
  "query": "SELECT * FROM `your-project.your-dataset.your-table`"
}'
```

Query result will not be prettily formatted.

# How to request Gemini

```bash
curl --request GET 'http://localhost:8080/gemini' \
--header 'Content-Type: application/json' \
--data '{
  "prompt": "Hello!"
}'
```

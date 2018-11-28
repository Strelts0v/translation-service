# APX Translation service

## Launching app

1. Go to the project root directory (translation-service) and build project using maven:
`
mvn install
`

2. After building project using maven open folder "target" and move file messages_en.properties file near the translationService.jar

3. Open folder "target" in console and input:
`
java -jar translationService.jar"
`

4. After 8 minutes of executing (on my local machine) you'll see in target folder generated messages files:

- messages_de.properties
- messages_es.properties
- messages_es_419.properties
- messages_fr.properties
- messages_hi.properties
- messages_it.properties
- messages_ja.properties
- messages_pl.properties
- messages_zh_CN.properties
- etc ...

## Issues

### 1. Expiring of API key

Sometimes new messages are not generated because the API key has been expired.

In this case execute the following steps:

1) Go to the https://translate.yandex.ru/developers/keys (log in or create an account if you haven't one)

2) Create new key on the page mentioned above (something like: 'trnsl.1.1.20181127T123639Z.2c861f5de3f878aa.049a25c35726c890914ca7227196e231e2cd8b0c')

3) Copy it and go the the folder '\translation-service\src\main\resources' (navigation regarding project root structure)

4) Open file 'settings.properties' and replace current value of key 'yandex.api.key' with copied key. You'll get something like:
`
yandex.api.key=trnsl.1.1.20181127T123639Z.2c861f5de3f878aa.049a25c35726c890914ca7227196e231e2cd8b0c
`

5) Save the file and rebuild the project

## 2. Character limits per day
Yandex API have character limits for translation per day. So practically you can use this jar once a day. If you try to translate more characters than Yandex API permit per day than you'll get HTTP error 403. 

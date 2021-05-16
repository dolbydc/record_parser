# record_parser
Record Parsing with REST API

## Testing

Run unit tests and report code coverage

```
lein cloverage
```

## Linting

Run Eastwood to check lint rules

```
lein eastwood
```

## Code formatting

Check that the code conforms to the formatting rules, and fix it not.

```
lein cljfmt check
```

```
lein cljfmt fix
```

## Building and Running

There are two modes this application can run in, cli and web.  First build a jar to be run

### Build

```
lein uberjar
```

### Run CLI
These examples use three test input files in the `test_data` folder.  Each file has a different record delimiter as defined in the requirement, comma, space or pipe.

```
cd target
```

#### Load and sort by color
```
> java -jar record-parser-0.1.0-SNAPSHOT-standalone.jar  -f ../test_data/input.csv -f ../test_data/input.pl -f ../test_data/input.txt --sort-type color
2021-05-16 15:57:05.531:INFO::main: Logging initialized @1839ms to org.eclipse.jetty.util.log.StdErrLog
Dolby,Jack,dj@mail.com,black,5/2/1931
Dolby,Marilyn,dm@mail.com,blue,12/12/1850
Palingo,Cindy,dc@mail.com,blue,11/20/1901
Dolby,Donevan,dd@mail.com,green,5/31/1901
Dolby,Cameron,dc@mail.com,orange,12/2/1910
Dolby,Veronica,dv@mail.com,pink,7/1/1908
Dolby,Ben,db@mail.com,red,4/2/1933
Dolby,Christopher,dc@mail.com,red,9/9/1800
```

#### Load and sort by lastname
```
> java -jar record-parser-0.1.0-SNAPSHOT-standalone.jar  -f ../test_data/input.csv -f ../test_data/input.pl -f ../test_data/input.txt --sort-type name
2021-05-16 15:58:45.100:INFO::main: Logging initialized @1862ms to org.eclipse.jetty.util.log.StdErrLog
Palingo,Cindy,dc@mail.com,blue,11/20/1901
Dolby,Veronica,dv@mail.com,pink,7/1/1908
Dolby,Cameron,dc@mail.com,orange,12/2/1910
Dolby,Marilyn,dm@mail.com,blue,12/12/1850
Dolby,Christopher,dc@mail.com,red,9/9/1800
Dolby,Ben,db@mail.com,red,4/2/1933
Dolby,Jack,dj@mail.com,black,5/2/1931
Dolby,Donevan,dd@mail.com,green,5/31/1901
```

#### Load and sort by birthdate
```
> java -jar record-parser-0.1.0-SNAPSHOT-standalone.jar  -f ../test_data/input.csv -f ../test_data/input.pl -f ../test_data/input.txt --sort-type date
2021-05-16 15:59:40.329:INFO::main: Logging initialized @1812ms to org.eclipse.jetty.util.log.StdErrLog
Dolby,Christopher,dc@mail.com,red,9/9/1800
Dolby,Marilyn,dm@mail.com,blue,12/12/1850
Dolby,Donevan,dd@mail.com,green,5/31/1901
Palingo,Cindy,dc@mail.com,blue,11/20/1901
Dolby,Veronica,dv@mail.com,pink,7/1/1908
Dolby,Cameron,dc@mail.com,orange,12/2/1910
Dolby,Jack,dj@mail.com,black,5/2/1931
Dolby,Ben,db@mail.com,red,4/2/1933
```

### Run web app

This mode of operation will load the input files into the internal store and then start a webapp that exposes four endpoints

| Action | Endpoint  | Description |
| --- | --- | --- |
| POST | /records | Post a new record to the store using comma, space or pipe delimited fields.
| GET | /records/color | Return array of JSON objects sorted by color then last name.
| GET | /records/birthdate | Return array of JSON objects sorted by birthdate.
| GET | /records/name | Return array of JSON objects sorted by last name descending to match the sort in the CLI.

```
> java -jar record-parser-0.1.0-SNAPSHOT-standalone.jar  -f ../test_data/input.csv -f ../test_data/input.pl -f ../test_data/input.txt --web
2021-05-16 16:06:37.502:INFO::main: Logging initialized @1816ms to org.eclipse.jetty.util.log.StdErrLog
2021-05-16 16:06:39.731:INFO:oejs.Server:main: jetty-9.4.40.v20210413; built: 2021-04-13T20:42:42.668Z; git: b881a572662e1943a14ae12e7e1207989f218b74; jvm 11.0.9+11-LTS
2021-05-16 16:06:39.819:INFO:oejs.AbstractConnector:main: Started ServerConnector@23b71d24{HTTP/1.1, (http/1.1)}{0.0.0.0:8080}
2021-05-16 16:06:39.821:INFO:oejs.Server:main: Started @4135ms
```

The endpoints can be hit using curl to test out.  Like the following example, where it will get all the records loading initially sorted by `birthdate`

```
> curl localhost:8080/records/birthdate
[{"last-name":"Dolby","first-name":"Christopher","email":"dc@mail.com","favorite-color":"red","date-of-birth":"9\/9\/1800"},{"last-name":"Dolby","first-name":"Marilyn","email":"dm@mail.com","favorite-color":"blue","date-of-birth":"12\/12\/1850"},{"last-name":"Dolby","first-name":"Donevan","email":"dd@mail.com","favorite-color":"green","date-of-birth":"5\/31\/1901"},{"last-name":"Palingo","first-name":"Cindy","email":"dc@mail.com","favorite-color":"blue","date-of-birth":"11\/20\/1901"},{"last-name":"Dolby","first-name":"Veronica","email":"dv@mail.com","favorite-color":"pink","date-of-birth":"7\/1\/1908"},{"last-name":"Dolby","first-name":"Cameron","email":"dc@mail.com","favorite-color":"orange","date-of-birth":"12\/2\/1910"},{"last-name":"Dolby","first-name":"Jack","email":"dj@mail.com","favorite-color":"black","date-of-birth":"5\/2\/1931"},{"last-name":"Dolby","first-name":"Ben","email":"db@mail.com","favorite-color":"red","date-of-birth":"4\/2\/1933"}]
```
Now add a new record using the post

```
> curl -X POST localhost:8080/records -d "Dolby Lalena dl@mail.com yellow 1/1/1900"
OK
```

And now we can run the query again and see this new record included:

```
> curl localhost:8080/records/birthdate
[{"last-name":"Dolby","first-name":"Christopher","email":"dc@mail.com","favorite-color":"red","date-of-birth":"9\/9\/1800"},{"last-name":"Dolby","first-name":"Marilyn","email":"dm@mail.com","favorite-color":"blue","date-of-birth":"12\/12\/1850"},{"last-name":"Dolby","first-name":"Lalena","email":"dl@mail.com","favorite-color":"yellow","date-of-birth":"1\/1\/1900"},{"last-name":"Dolby","first-name":"Donevan","email":"dd@mail.com","favorite-color":"green","date-of-birth":"5\/31\/1901"},{"last-name":"Palingo","first-name":"Cindy","email":"dc@mail.com","favorite-color":"blue","date-of-birth":"11\/20\/1901"},{"last-name":"Dolby","first-name":"Veronica","email":"dv@mail.com","favorite-color":"pink","date-of-birth":"7\/1\/1908"},{"last-name":"Dolby","first-name":"Cameron","email":"dc@mail.com","favorite-color":"orange","date-of-birth":"12\/2\/1910"},{"last-name":"Dolby","first-name":"Jack","email":"dj@mail.com","favorite-color":"black","date-of-birth":"5\/2\/1931"},{"last-name":"Dolby","first-name":"Ben","email":"db@mail.com","favorite-color":"red","date-of-birth":"4\/2\/1933"}]
```


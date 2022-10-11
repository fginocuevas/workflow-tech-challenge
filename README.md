
# Technical Challenge Task

A brief description of how to run and use the project. 




## Running the project

To run this project, navigate to the root folder and run the command

```bash
  mvn spring-boot:run
```

The application, by default, will use the local port 8091.



## API Reference

First step will always be to generate a reference table of the module dependencies, which will be stored into a H2 database.

NOTE: At the moment, you will need to restart the application everytime you will use a new reference table.
In the future, we can delete or disable the existing data everytime the Generate the Module Reference Table is invoked.

#### Generate the Module Reference Table

```http
  POST /module-node
```

Example #1

```
{
    "moduleKey" : "A",
    "children" : [
        {
            "moduleKey": "B"
        },
        {
            "moduleKey" : "C",
            "children" : [
                {
                    "moduleKey": "E"
                },
                {
                    "moduleKey": "F"
                },
                {
                    "moduleKey": "G",
                    "children" : [
                        {
                            "moduleKey": "H"
                        },
                        {
                            "moduleKey": "I"
                        }
                    ]
                }
            ]
        },
        {
            "moduleKey": "D"
        }
    ]
}
```

Example #2
```
{
    "moduleKey" : "A",
    "children" : [
        {
            "moduleKey": "B"
        },
        {
            "moduleKey" : "C",
            "children" : [
                {
                    "moduleKey": "E"
                },
                {
                    "moduleKey": "F"
                },
                {
                    "moduleKey": "G",
                    "children" : [
                        {
                            "moduleKey": "H"
                        },
                        {
                            "moduleKey": "I"
                        }
                    ]
                },
                {
                    "moduleKey": "E"
                }
            ]
        },
        {
            "moduleKey": "D"
        }
    ]
}
```
Example #3
```
{
    "moduleKey" : "A",
    "children" : [
        {
            "moduleKey": "B"
        },
        {
            "moduleKey" : "C",
            "children" : [
                {
                    "moduleKey": "E"
                },
                {
                    "moduleKey": "F"
                },
                {
                    "moduleKey": "G",
                    "children" : [
                        {
                            "moduleKey": "H"
                        },
                        {
                            "moduleKey": "I",
                            "children" : [
                                {
                                    "moduleKey": "C"
                                }
                            ]
                        }
                    ]
                },
                {
                    "moduleKey": "E"
                }
            ]
        },
        {
            "moduleKey": "D"
        }
    ]
}    
```

#### Retrieving the Module Dependencies aka getModuleDependencies()

```http
  GET /module-node/dependencies/${key}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. Module Id to fetch |

Sample request
```http
GET /module-node/dependencies/A
```

Sample response
```
[
    "B",
    "E",
    "F",
    "H",
    "I",
    "G",
    "C",
    "D"
]
```


## Documentation

- Known Bugs:
    - If a module has been used more than once in differents parents, will encounter IncorrectResultSizeDataAccessException because there will be two matching rows in the database. Possible solution could be to validation and check if the module exists in the database, then retrieve and assign the existing module instead.
## Support

For support, email fake@fake.com.


## ENDPOINTS

#### Register user

```http
  POST localhost:8080/users
```

JSON:

```JSON
{
	"username": "KauanCavazani",
	"email": "kauan@email.com",
	"password": "1234",
	"profilePictureUrl": "./pictures/kauan.jpeg"
}
```

#### Create group

```http
  POST localhost:8080/groups
```

JSON:

```JSON
{
	"idAdmin": 1,
	"name": "grupo do bairro",
	"profilePictureUrl": "./pictures/group.jpeg",
	"idMembers": [
		1
	]
}
```

#### Add member to group

```http
  POST localhost:8080/groups/<idGroup>/<idAdmin>/<idMember>
```

#### Get users

```http
  GET localhost:8080/users
```

#### Get groups by id member

```http
  GET localhost:8080/groups/<idMember>
```

#### Login

```http
  PATCH localhost:8080/users
```

JSON:

```JSON
{
	"email": "kauan@email.com",
	"password": "1234",
}
```

#### Logoff

```http
  PATCH localhost:8080/users/<idUser>
```

#### Delete group member 

```http
  DELETE localhost:8080/groups/<idGroup>/<idAdmin>/<idMember>
```

#### Leave the group

```http
  DELETE localhost:8080/groups/<idGroup>/<idMember>
```


## Author

- [@KauanCavazani](https://www.github.com/KauanCavazani)

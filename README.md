# rptd

RPTD (Rapid ProtoType Deployment) is a Spring Application that provides rapid deployment of 
prototype Restful APIs, allowing users to add and modify its behavior at runtime using 
scripts written in [BNL](https://github.com/dayal96/bnl-lang). These scripts can be managed 
(added, modified, deleted) using the RPTD REST API.

## RPTD API

RPTD hosts two APIs - the RPTD API for managing Route Entries hosted by RPTD, and the Prototype API
that hosts the modifiable behaviors of the server based on the Route Entries.

### Route Entry

A Route Entry has two components - the pattern of HTTP requests that match the Route Entry and 
the BNL script that computes the response for that request.

```
GET /test           <--- [Request Type] [uri]

"Test Works!"       <--- BNL expression that evaluates the response
```

URI specifiers in Route Entries can also be used to specify route parameters that will be
available as strings for use in the BNL expression.

```
GET /test/{testData}                        <---- will bind URI parameter to testData

(string-append "Route Param : " testData)   <---- testData can be used like any other BNL variable
```

For POST and PUT Route Entries, RPTD will also deserialize `application/json` request bodies 
into BNL expressions for use in the response.

```
POST /test

(make-object (list (cons "request-body" requestBody)))

```

All routes defined by Route Entry will consume and produce `application/json` data. The BNL 
expressions that are evaluated by the Route Entry will be serialised into JSON before they are 
added to the response body.

Route Entries are `POST`ed to `/rptd/route` in `text/plain` format.
Active Route Entries can be listed using `GET /rptd/routes`, which will return a JSON response with
all route entries and their ids. These ids can be used to modify Route Entries with a PUT request on
`/rptd/route/{id}` with new Route Entry as body in `text/plain` format, or fetched and deleted 
using `GET` and `DELETE` respectively at `/rptd/route/{id}`.


### Route Priority

If there are multiple matching routes for any request, the route priority is determined based on 
number of exact matches in the Route Entry uri and the request url. Route Entry URI Parameters 
do not count as exact matches and are given lower priority than non-parametric matches. In case 
of ties, only one of the tied routes is selected.

For instance, when matching a request for `/test/route/one`, the matching routes would be 
prioritised in this order:
1. `/test/route/one`
2. `/test/route/{var}`, `/test/{var}/one`, `/{var}/route/one`
3. `/test/{var1}/{var2}`, `/{var1}/route/{var2}`, `/{var1}/{var2}/one`
4. `/{var1}/{var2}/{var3}`


### Extended BNL

RPTD extends BNL for interacting with JSON objects - `make-object` and `lookup`

```
# equivalent to {"key": "value", "num": 15} in JSON
(define JSON-OBJ-1 (make-object (list (cons "key" "value")
                                      (cons "num" 15)))

# evaluates to "value"
(lookup JSON-OBJ-1 "key")

# evaluates to false if the field is not present in the object
(lookup JSON-OBJ-1 "id")
```

RPTD also adds a pseudo-random function that is seeded with the actual request url -

```
GET /request/random/{var}

(random 100)     <----- will return the same integer (0 <= n < 100) for same {var}
```

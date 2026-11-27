
# ClientErrorReport

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | The error&#39;s &#x60;name&#x60;, such as &#x60;TypeError&#x60;. |  |
| **message** | **kotlin.String** | The error&#39;s &#x60;message&#x60;. |  |
| **stack** | **kotlin.String** | The error&#39;s &#x60;stack&#x60; as the browser formats it. |  |
| **kind** | [**inline**](#Kind) | How the error was caught: &#x60;route_error&#x60; by the router, &#x60;boundary_error&#x60; by an error boundary outside the routes, &#x60;uncaught&#x60; outside every boundary, and &#x60;unhandledrejection&#x60; as a promise rejection that nothing handled.  |  |
| **release** | **kotlin.String** | The version of the server, from the page that started the web client. |  [optional] |
| **route** | **kotlin.String** | The ID of the route that was shown (a pattern such as &#x60;/_app/statuses/$id&#x60;, never the URL).  |  [optional] |


<a id="Kind"></a>
## Enum: kind
| Name | Value |
| ---- | ----- |
| kind | route_error, boundary_error, uncaught, unhandledrejection |





# Event

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | [**java.util.UUID**](java.util.UUID.md) | The ID of a resource, a UUIDv7 in its canonical string form. |  |
| **organizer** | [**Account**](Account.md) | The organizer; &#x60;unavailable&#x60; when the user may not see the account (it blocks the user, is deactivated or suspended, or moderation hides its profile).  |  |
| **coorganizers** | [**kotlin.collections.List&lt;Account&gt;**](Account.md) | The co-organizers, in the order the organizer named them; up to 3. Those the user may not see, as for &#x60;organizer&#x60;, are left out.  |  |
| **title** | **kotlin.String** |  |  |
| **description** | **kotlin.String** | Plain text; empty for none. |  |
| **visibility** | [**EventVisibility**](EventVisibility.md) |  |  |
| **timeZone** | **kotlin.String** | The IANA name of the event&#39;s time zone, such as &#x60;Asia/Tokyo&#x60;. |  |
| **placeDisclosure** | [**EventPlaceDisclosure**](EventPlaceDisclosure.md) |  |  |
| **capacity** | **kotlin.Int** | How many attendees may answer &#x60;going&#x60;; 0 for no limit. |  |
| **boardWriters** | [**EventBoardWriters**](EventBoardWriters.md) |  |  |
| **places** | [**kotlin.collections.List&lt;EventPlace&gt;**](EventPlace.md) |  |  |
| **hiddenPlaces** | [**kotlin.collections.List&lt;EventPlaceKind&gt;**](EventPlaceKind.md) | The kinds of the places that the user does not see. |  |
| **startsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | The start of the event; for an &#x60;unscheduled&#x60; event, the earliest start of its candidate dates (&#x60;GET /events/{id}/candidates&#x60;).  |  |
| **endsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | The end of the event; for an &#x60;unscheduled&#x60; event, the latest end of its candidate dates.  |  |
| **state** | [**EventState**](EventState.md) |  |  |
| **goingCount** | **kotlin.Int** | How many attendees answer &#x60;going&#x60;. |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **revisedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | When the settings were last changed (the creation for an event never changed). |  |
| **revisionCount** | **kotlin.Int** | How many versions the settings have had, the first included. |  |
| **viewer** | [**EventViewer**](EventViewer.md) |  |  |
| **cancellation** | [**EventCancellation**](EventCancellation.md) |  |  [optional] |





# EventCreate

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **title** | **kotlin.String** | 1 to 100 characters on one line. |  |
| **description** | **kotlin.String** | Up to 3,000 characters of plain text; line breaks are kept. |  [optional] |
| **visibility** | [**EventVisibility**](EventVisibility.md) | Who sees the event; &#x60;public&#x60; when omitted. |  [optional] |
| **timeZone** | **kotlin.String** | The IANA name of the event&#39;s time zone; the user&#39;s time zone when omitted. |  [optional] |
| **placeDisclosure** | [**EventPlaceDisclosure**](EventPlaceDisclosure.md) | Who sees the &#x60;physical&#x60; and &#x60;url&#x60; places; &#x60;approved&#x60; when omitted. |  [optional] |
| **capacity** | **kotlin.Int** | How many attendees may answer &#x60;going&#x60;, up to 10,000; 0 (the default) for no limit. |  [optional] |
| **boardWriters** | [**EventBoardWriters**](EventBoardWriters.md) | Who posts on the event&#39;s board; &#x60;coorganizers&#x60; when omitted. |  [optional] |
| **places** | [**kotlin.collections.List&lt;EventPlace&gt;**](EventPlace.md) | Up to 3 places, one of each kind. |  [optional] |
| **startsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | After now and at most a year ahead. |  [optional] |
| **endsAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) | After &#x60;starts_at&#x60; and at most 7 days later. |  [optional] |
| **candidates** | [**kotlin.collections.List&lt;EventCandidateCreate&gt;**](EventCandidateCreate.md) | 1 to 20 different candidate dates, each checked as a fixed date: the event is &#x60;unscheduled&#x60; until the organizers confirm one of them.  |  [optional] |




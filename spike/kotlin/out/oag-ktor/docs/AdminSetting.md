
# AdminSetting

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **key** | [**InstanceSettingKey**](InstanceSettingKey.md) |  |  |
| **kind** | [**InstanceSettingKind**](InstanceSettingKind.md) |  |  |
| **&#x60;value&#x60;** | **kotlin.String** | The current value, which is &#x60;default&#x60; when &#x60;stored&#x60; is false. |  |
| **default** | **kotlin.String** | The value of the setting while it has never been set. |  |
| **stored** | **kotlin.Boolean** | Whether the setting has been set; false means its default applies. |  |
| **choices** | **kotlin.collections.List&lt;kotlin.String&gt;** | The values an &#x60;enum&#x60; setting accepts, in order; omitted for other kinds. |  [optional] |
| **minimum** | **kotlin.Int** | The least value of an &#x60;int&#x60; setting; omitted for other kinds. |  [optional] |
| **maximum** | **kotlin.Int** | The greatest value of an &#x60;int&#x60; setting; omitted for other kinds. |  [optional] |
| **image** | [**AdminSettingImage**](AdminSettingImage.md) | For a &#x60;media&#x60; setting with a value, its image, so that a client can show what is set whoever uploaded it; omitted for other kinds, for none, and while the file is processing or is not an image.  |  [optional] |




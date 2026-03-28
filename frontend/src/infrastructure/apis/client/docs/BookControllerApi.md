# BookControllerApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addBook**](BookControllerApi.md#addbook) | **POST** /book/addBook |  |
| [**getBooksByAuthor**](BookControllerApi.md#getbooksbyauthor) | **GET** /book/getByAuthor |  |



## addBook

> Book addBook(bookDto)



### Example

```ts
import {
  Configuration,
  BookControllerApi,
} from '';
import type { AddBookRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const config = new Configuration({ 
    // Configure HTTP bearer authorization: bearerAuth
    accessToken: "YOUR BEARER TOKEN",
  });
  const api = new BookControllerApi(config);

  const body = {
    // BookDto
    bookDto: ...,
  } satisfies AddBookRequest;

  try {
    const data = await api.addBook(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bookDto** | [BookDto](BookDto.md) |  | |

### Return type

[**Book**](Book.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getBooksByAuthor

> Array&lt;BookDto&gt; getBooksByAuthor(author)



### Example

```ts
import {
  Configuration,
  BookControllerApi,
} from '';
import type { GetBooksByAuthorRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const config = new Configuration({ 
    // Configure HTTP bearer authorization: bearerAuth
    accessToken: "YOUR BEARER TOKEN",
  });
  const api = new BookControllerApi(config);

  const body = {
    // string
    author: author_example,
  } satisfies GetBooksByAuthorRequest;

  try {
    const data = await api.getBooksByAuthor(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **author** | `string` |  | [Defaults to `undefined`] |

### Return type

[**Array&lt;BookDto&gt;**](BookDto.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


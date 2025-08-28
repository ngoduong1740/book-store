# 📚 Book API Documentation

**Base URL**: `/api/books`
**Content-Type**: `application/json`

---

## 1. CRUD Operations

### ➤ Get all books

* **GET** `/api/books`
* **Response**:

  * `200 OK` → Danh sách sách
  * `500 INTERNAL_SERVER_ERROR`

---

### ➤ Get book by ID

* **GET** `/api/books/{id}`
* **Path Param**: `id` (Long)
* **Response**:

  * `200 OK` → Thông tin sách
  * `404 NOT FOUND`
  * `500 INTERNAL_SERVER_ERROR`

---

### ➤ Create book

* **POST** `/api/books`
* **Body**:

```json
{
  "title": "Book title",
  "author": "Author name",
  "category": "Novel",
  "isbn": "1234567890",
  "price": 100.0,
  "stock": 10
}
```

* **Response**:

  * `201 CREATED` → Trả về book vừa tạo
  * `400 BAD REQUEST`
  * `500 INTERNAL_SERVER_ERROR`

---

### ➤ Update book

* **PUT** `/api/books/{id}`
* **Path Param**: `id` (Long)
* **Body**: Giống `POST`
* **Response**:

  * `200 OK` → Trả về book sau khi update
  * `404 NOT FOUND`
  * `400 BAD REQUEST`
  * `500 INTERNAL_SERVER_ERROR`

---

### ➤ Delete book

* **DELETE** `/api/books/{id}`
* **Path Param**: `id` (Long)
* **Response**:

  * `200 OK` → `{ "message": "Book deleted successfully" }`
  * `404 NOT FOUND`
  * `400 BAD REQUEST`
  * `500 INTERNAL_SERVER_ERROR`

---

## 2. Search Operations

### ➤ Search by title

* **GET** `/api/books/search/title?title={title}`

### ➤ Search by author

* **GET** `/api/books/search/author?author={author}`

### ➤ Search by category

* **GET** `/api/books/search/category?category={category}`

**Response chung**:

* `200 OK` → Danh sách sách tìm được
* `500 INTERNAL_SERVER_ERROR`

---

### ➤ Find by ISBN

* **GET** `/api/books/isbn/{isbn}`
* **Response**:

  * `200 OK` → Trả về sách
  * `404 NOT FOUND`
  * `500 INTERNAL_SERVER_ERROR`

---

## 3. Inventory Operations

### ➤ Get in-stock books

* **GET** `/api/books/inventory/in-stock`

### ➤ Get out-of-stock books

* **GET** `/api/books/inventory/out-of-stock`

### ➤ Get low-stock books

* **GET** `/api/books/inventory/low-stock?threshold={number}` (default = 5)

**Response**:

* `200 OK` → Danh sách sách
* `500 INTERNAL_SERVER_ERROR`

---

### ➤ Update stock

* **PUT** `/api/books/{id}/stock?quantity={number}`
* **Response**:

  * `200 OK` → Trả về book đã update stock
  * `404 NOT FOUND`
  * `400 BAD REQUEST`
  * `500 INTERNAL_SERVER_ERROR`

---

## 4. Statistics

### ➤ Get inventory stats

* **GET** `/api/books/stats`
* **Response** (ví dụ):

```json
{
  "totalBooks": 100,
  "inStock": 80,
  "outOfStock": 15,
  "lowStock": 5
}
```

---

### ➤ Get all categories

* **GET** `/api/books/categories`
* **Response**:

  * `200 OK` → Danh sách category (array of string)
  * `500 INTERNAL_SERVER_ERROR`

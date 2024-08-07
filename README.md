<div align="center">

# 📖 HowRead

<img width="300px" src="https://github.com/sadang-boogi/howread-back/assets/120021021/3fafbae0-71a1-45e3-acb8-281f0c722c75" alt="HowRead"/>

</div>

## CI/CD

![cicd](https://github.com/user-attachments/assets/14a527ef-6416-426e-b31d-d25fce202c98)
 
## ER Diagram

```mermaid
classDiagram
    direction BT
    class book {
        tinyint(1) is_deleted
        datetime(6) created_at
        datetime(6) deleted_at
        datetime(6) updated_at
        varchar(255) author
        varchar(255) thumbnail_url /* 책 대표 표지 이미지 */
        varchar(255) title
        varchar(17) isbn
        bigint id
    }
    class book_hashtag {
        tinyint(1) is_deleted
        bigint book_id
        datetime(6) created_at
        datetime(6) deleted_at
        bigint hashtag_id
        datetime(6) updated_at
        bigint id
    }
    class hashtag {
        tinyint(1) is_deleted
        datetime(6) created_at
        datetime(6) deleted_at
        datetime(6) updated_at
        varchar(255) name /* 해시태그 이름 */
        bigint id
    }

    class reaction {
        varchar(255) reaction_type
        varchar(255) target_type
        bigint target_id
        bigint user_id
        bit(1) is_on
        bigint id
    }
    class review {
        tinyint(1) is_deleted
        decimal(3, 2) score /* 평점 */
        bigint book_id /* 리뷰 도서 */
        datetime(6) created_at
        datetime(6) deleted_at
        datetime(6) updated_at
        bigint user_id
        varchar(255) content /* 리뷰 내용 */
        bigint id
    }
    class study_group {
        varchar(255) name
        varchar(255) description
        int max_members
        datetime(6) created_at
        datetime(6) updated_at
        bit(1) is_deleted
        datetime(6) deleted_at
        bigint id
    }
    class study_group_member {
        bigint study_group_id
        bigint user_id
        varchar(255) grade
        datetime(6) created_at
        datetime(6) updated_at
        bit(1) is_deleted
        datetime(6) deleted_at
        bigint id
    }
    class users {
        tinyint(1) is_deleted
        datetime(6) created_at
        datetime(6) deleted_at
        datetime(6) updated_at
        varchar(255) email
        varchar(255) nickname
        varchar(255) social_id
        varchar(255) role
        varchar(255) social_type
        varchar(255) avatar_url
        bigint id
    }

    book_hashtag --> book: book_id
    book_hashtag --> hashtag: hashtag_id
    reaction --> users: user_id
    review --> book: book_id
    review --> users: user_id
    study_group_member --> study_group: study_group_id
    study_group_member --> users: user_id
```

## flyway 절차

1. `src/main/resources/db/migration` 디렉토리에 `V{version}__{description}.sql` 파일을 생성합니다.
2. `./gradlew flywayMigrate` 명령어를 실행합니다. (마이그레이션 시 환경변수에 DB_URL, DB_USERNAME, DB_PASSWORD가 필요합니다.)

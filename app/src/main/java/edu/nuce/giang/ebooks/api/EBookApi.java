package edu.nuce.giang.ebooks.api;

import java.util.List;

import edu.nuce.giang.ebooks.models.AuthorModel;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.models.CheckLoginModel;
import edu.nuce.giang.ebooks.models.CollectionModel;
import edu.nuce.giang.ebooks.models.CommentModel;
import edu.nuce.giang.ebooks.models.StatusModel;
import edu.nuce.giang.ebooks.models.UserModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EBookApi {

    @GET("books/ids")
    Call<List<BookModel>> findAllBookByIds(@Query("id") List<Integer> ids);

    @GET("comments/{bookId}")
    Call<List<CommentModel>> findCommentsOfBook(@Path("bookId") int bookId);

    @GET("categories")
    Call<List<CollectionModel>> findAllCollections();

    @GET("authors")
    Call<List<AuthorModel>> findAllAuthors();

    @GET("books")
    Call<List<BookModel>> findAllBooks();

    @GET("author/{authorId}")
    Call<List<AuthorModel>> findOneAuthor(@Path("authorId") int authorId);

    @GET("category/{categoryId}")
    Call<List<CollectionModel>> findOneCategory(@Path("categoryId") int categoryId);

    @GET("books/author")
    Call<List<BookModel>> findAllBooksByAuthorId(@Query("authorId") int authorId);

    @GET("book/{id}")
    Call<List<BookModel>> findOneBookByBookId(@Path("id") int bookId);

    @GET("books/filter")
    Call<List<BookModel>> resultsFilterBooks(@Query("v") String value);

    @GET("books/category")
    Call<List<BookModel>> findAllBooksByCategoryId(@Query("categoryId") int categoryId);

    @FormUrlEncoded
    @POST("user")
    Call<CheckLoginModel> checkLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @Multipart
    @POST("user/update")
    Call<CheckLoginModel> updateImageUser(
        @Part("userId") RequestBody userId,
        @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("users")
    Call<StatusModel> saveUser(
        @Field("fullname") String fullName,
        @Field("username") String username,
        @Field("password") String password,
        @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("comments")
    Call<StatusModel> saveComment(
        @Field("userId") int userId,
        @Field("bookId") int bookId,
        @Field("content") String content,
        @Field("score") float score
    );
}

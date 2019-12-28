package edu.nuce.giang.ebooks.activities.detail;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.activities.read.EBooksReadActivity;
import edu.nuce.giang.ebooks.adapters.FictionTabAdapter;
import edu.nuce.giang.ebooks.adapters.FictionTabAdapterSelected;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Medium;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Regular;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.activities.WrapContentHeightViewPager;
import edu.nuce.giang.ebooks.models.LibraryModel;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.BookView;

public class EBookFictionActivity extends AppCompatActivity implements BookView,
        FictionTabAdapterSelected {

    @BindView(R.id.txtToolbarTitle)
    MyTextView_Roboto_Medium txt;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    WrapContentHeightViewPager wrapContentHeightViewPager;
    @BindView(R.id.book_image)
    ImageView bookImage;
    @BindView(R.id.book_name)
    MyTextView_Roboto_Medium bookName;
    @BindView(R.id.book_publisher)
    MyTextView_Roboto_Regular bookPublisher;
    @BindView(R.id.book_read)
    Button bookRead;
    @BindView(R.id.ic_back)
    ImageView onBack;
    @BindView(R.id.btnBooksMark)
    ImageButton btnBooksMark;

    BookPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_fiction);

        ButterKnife.bind(this);

        txt.setText("Detail Book");
        setUpTabLayoutAndViewPager();

        presenter = new IBookPresenter(this);
        Intent intent = getIntent();
        BookModel model = (BookModel) intent.getSerializableExtra("book");
        if (model.getId() != null) {
            presenter.getData(model.getId());
        }

        bookRead.setOnClickListener(v -> {
            Intent intent1 = new Intent(EBookFictionActivity.this,
                    EBooksReadActivity.class);
            intent1.putExtra("bookId", model.getId());
            startActivity(intent1);
        });

        btnBooksMark.setOnClickListener(v -> {
            try {
                LibraryModel libraryModel = Utils.getDataBaseUtilsInstance(EBookFictionActivity.this)
                        .getBook(model.getId());
                if (libraryModel == null) {
                    Utils.getDataBaseUtilsInstance(EBookFictionActivity.this)
                            .addBook(new LibraryModel(
                                    model.getId(),
                                    0,
                                    0
                            ));
                    new CustomSweetAlertDialog(EBookFictionActivity.this)
                            .alertDialogSuccess(
                                    "Successfully!"
                                    , "Your books has been saved to books mark!"
                            );
                } else {
                    new CustomSweetAlertDialog(EBookFictionActivity.this)
                            .alertDialogError(
                                    "Failure!",
                                    "Your book has been had in books mark!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        onBack.setOnClickListener(v -> {
            finish();
        });

    }

    private void setUpTabLayoutAndViewPager() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("DETAIL"));
        tabLayout.addTab(tabLayout.newTab().setText("REVIEWS"));
        tabLayout.addTab(tabLayout.newTab().setText("RELATED"));

        tabLayout.setTabGravity(TabLayout.MODE_FIXED);
        FictionTabAdapter tabAdapter1 = new FictionTabAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount(),
                this);
        wrapContentHeightViewPager.setAdapter(tabAdapter1);

        wrapContentHeightViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                wrapContentHeightViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public Fragment getItemFragment(int position) {
        switch (position) {
            case 0:
                EBookFragment fragment = new EBookFragment();
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                BookModel model = (BookModel) intent.getSerializableExtra("book");
                bundle.putInt("id", model.getId());
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                EBookReviewFragment reviewFragment = new EBookReviewFragment();
                Bundle args = new Bundle();
                BookModel modelBook = (BookModel) getIntent().getSerializableExtra("book");
                args.putInt("id", modelBook.getId());
                reviewFragment.setArguments(args);
                return reviewFragment;
            case 2:
                EBookFrictionFragment EBookFrictionFragment = new EBookFrictionFragment();
                Bundle bundle1 = new Bundle();
                Intent intent1 = getIntent();
                BookModel model1 = (BookModel) intent1.getSerializableExtra("book");
                bundle1.putInt("authorId", model1.getAuthorId());
                bundle1.putInt("categoryId", model1.getIdCategory());
                bundle1.putString("authorName", model1.getAuthorName());
                EBookFrictionFragment.setArguments(bundle1);
                return EBookFrictionFragment;
            default:
                return null;
        }
    }

    @Override
    public void setData(BookModel model) {
        Picasso.get()
                .load(Utils.URL + model.getUrlImage())
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(bookImage);
        bookName.setText(model.getName());
        bookPublisher.setText(model.getPublisher());
    }

    @Override
    public void onError(String error) {
        new CustomSweetAlertDialog(this)
                .alertDialogError("Error!", error);
    }

    @Override
    public void setFilterResultsBooks(List<BookModel> models) {

    }

    @Override
    public void setRelatedBooksCategory(List<BookModel> models) {

    }

    @Override
    public void setRelatedBooksAuthor(List<BookModel> models) {

    }

    @Override
    public void setListData(List<BookModel> models) {

    }

    @Override
    public void loadingData() {

    }

    @Override
    public void hideLoadingData() {

    }
}





package edu.nuce.giang.ebooks.activities.read;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Regular;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.models.LibraryModel;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.BookView;

public class EBooksReadActivity extends AppCompatActivity implements BookView {
    @BindView(R.id.pdfViewer)
    PDFView pdfView;
    @BindView(R.id.pagePerPages)
    MyTextView_Roboto_Regular pagePerPages;
    @BindView(R.id.seekBar2)
    SeekBar seekBar;
    @BindView(R.id.pageLoading)
    ConstraintLayout pageLoading;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView toolbarTitle;
    @BindView(R.id.bottom_page)
    LinearLayout layout;

    private int currentPage = 0;
    private int totalPages = 0;
    private int bookId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_read);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        int bookId = intent.getIntExtra("bookId", 0);
        if (bookId != 0) {
            BookPresenter presenter = new IBookPresenter(this);
            presenter.getData(bookId);
        }

        setUpActionBar();
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbarTitle.setSelected(true);

        }
        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(
                    R.color.white),
                    PorterDuff.Mode.SRC_ATOP
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pdf_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    LibraryModel model = Utils.getDataBaseUtilsInstance(getApplicationContext())
                            .getBook(bookId);
                    if (model != null) {
                        if (currentPage >= model.getPageCurrent()) {
                            if (bookId != 0) {
                                Utils.getDataBaseUtilsInstance(getApplicationContext())
                                        .updateNote(new LibraryModel(
                                                model.getId(),
                                                bookId,
                                                currentPage + 1,
                                                totalPages
                                        ));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            case R.id.fullScreen:
                showToolbar(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToolbar(boolean show) {
        if (show) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            layout.setVisibility(View.VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            layout.setVisibility(View.GONE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        }
    }

    private void loadingPage(int page) {
        int timeOut = Math.abs(page - currentPage) * 10;
        pageLoading.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            pageLoading.setVisibility(View.GONE);
        }, timeOut);
        new Handler().postDelayed(() -> {
            pdfView.jumpTo(page);
        }, timeOut + 100);
    }

    @Override
    public void setListData(List<BookModel> models) {

    }

    @Override
    public void setData(BookModel model) {
        bookId = model.getId();
        toolbarTitle.setText(model.getName());
        FileLoader.with(this)
                .load(Utils.URL + model.getUrlContent(),
                        true)
                .asFile(new FileRequestListener<File>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        pdfView.fromFile(response.getBody())
                                .onLoad(nbPages -> {
                                    seekBar.setMax(nbPages - 1);
                                })
                                .defaultPage(0)
                                .enableSwipe(true)
                                .spacing(10)
                                .onPageChange((page, pageCount) -> {
                                    String pageToPages = page + 1 + " of " + pageCount;
                                    pagePerPages.setText(pageToPages);
                                    currentPage = page;
                                    totalPages = pageCount;
                                    seekBar.setProgress(page, true);
                                })
                                .onTap(event -> {
                                    showToolbar(true);
                                    return true;
                                })
                                .swipeHorizontal(true)
                                .pageSnap(true)
                                .autoSpacing(true)
                                .pageFling(true)
                                .load();

                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            int progressValue = 0;

                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                progressValue = progress;
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                loadingPage(progressValue);
                            }
                        });
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Log.e("tag", t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void loadingData() {
        pageLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        pageLoading.setVisibility(View.GONE);
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
}

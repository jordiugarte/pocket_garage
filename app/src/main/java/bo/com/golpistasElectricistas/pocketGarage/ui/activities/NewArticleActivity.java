package bo.com.golpistasElectricistas.pocketGarage.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bo.com.golpistasElectricistas.pocketGarage.R;
import bo.com.golpistasElectricistas.pocketGarage.model.Article;
import bo.com.golpistasElectricistas.pocketGarage.model.Base;
import bo.com.golpistasElectricistas.pocketGarage.repository.local.Local;
import bo.com.golpistasElectricistas.pocketGarage.utils.CompressImage;
import bo.com.golpistasElectricistas.pocketGarage.viewModel.NewArticleViewModel;

import static bo.com.golpistasElectricistas.pocketGarage.utils.Constants.FIREBASE_PATH_STORAGE_IMAGES;
import static bo.com.golpistasElectricistas.pocketGarage.utils.Constants.KEY_ARTICLES_QUANTITY;

public class NewArticleActivity extends AppCompatActivity {
    private Context context;

    private List<Bitmap> imageViews = new ArrayList<>();

    private LinearLayout imagesRow;
    private List<Uri> photos = new ArrayList<>();
    private EditText titleField, shortDescriptionField, descriptionField, priceField;
    private Switch newSwitch;
    private Spinner categorySpinner;

    private Intent mainActivity;

    private NewArticleViewModel viewModel;

    private static final String LOG = NewArticleActivity.class.getSimpleName();

    private int articlesQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_article);
        context = getApplicationContext();
        viewModel = new NewArticleViewModel(getApplication());
        initViews();
        initIntents();
        articlesQuantity = getIntent().getIntExtra(KEY_ARTICLES_QUANTITY, 0);
    }

    private void initIntents() {
        mainActivity = new Intent(context, MainActivity.class);
    }

    private void initViews() {
        imagesRow = findViewById(R.id.imagesArticleRow);
        titleField = findViewById(R.id.titleField);
        shortDescriptionField = findViewById(R.id.shortDescriptionField);
        descriptionField = findViewById(R.id.descriptionField);
        priceField = findViewById(R.id.priceField);
        newSwitch = findViewById(R.id.newSwitch);
        categorySpinner = findViewById(R.id.categorySpinner);
    }

    public void addPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Add picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap newBitmap;
                newBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Uri photo = CompressImage.compressImage(filePath, context);
                photos.add(photo);
                imagesRow.addView(generateImageView(newBitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ImageView generateImageView(Bitmap bitmap) {
        ImageView imageView = new ImageView(this);
        imageView.requestLayout();
        /*imageView.getLayoutParams().width = 100;
        imageView.getLayoutParams().height = 100;*/
        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.gradient4));
        imageView.setImageBitmap(bitmap);
        imageViews.add(bitmap);
        return imageView;
    }

    public void postArticle(View view) {
        if (!titleField.getText().toString().isEmpty() && !shortDescriptionField.getText().toString().isEmpty() &&
                !descriptionField.getText().toString().isEmpty() && !priceField.getText().toString().isEmpty() && !photos.isEmpty()) {
            Article article = new Article();
            article.setTitle(titleField.getText().toString());
            article.setUserId(new Local(context).getCurrentUser().getCi());
            article.setShortDescription(shortDescriptionField.getText().toString());
            article.setDescription(descriptionField.getText().toString());
            article.setPhotos(convertList(photos));
            article.setPrice(Double.parseDouble(priceField.getText().toString()));
            article.setNewState(newSwitch.isChecked());
            article.setCategory(categorySpinner.getSelectedItemPosition());
            article.setTimestamp(Calendar.getInstance().getTimeInMillis());
            article.setArticleId(articlesQuantity);
            viewModel.addArticle(article, photos).observe(this, new Observer<Base<Article>>() {
                @Override
                public void onChanged(Base<Article> stringBase) {
                    if (stringBase.isSuccess()) {
                        Log.e(LOG, "createPost.isSuccess:" + stringBase.getData());
                        startActivity(mainActivity);
                    } else {
                        Log.e(LOG, "createPost.error", stringBase.getException());
                    }
                }
            });
            startActivity(mainActivity);
        } else {
            Toast.makeText(context, context.getString(R.string.error_fill_values),
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void returnToPrevious(View view) {
        finish();
    }

    private List<String> convertList(List<Uri> list) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(FIREBASE_PATH_STORAGE_IMAGES + "%2F" + articlesQuantity + "%2F" + i + ".jpg?alt=media");
        }
        return result;
    }
}
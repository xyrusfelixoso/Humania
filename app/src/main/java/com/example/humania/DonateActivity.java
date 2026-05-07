package com.example.humania;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DonateActivity extends AppCompatActivity {


    private static final int CAMERA_PERMISSION_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int MAP_PICKER_REQUEST_CODE = 103;
    
    private LinearLayout uploadZone;
    private ImageView ivPreview;
    private String currentPhotoPath;
    
    private TextView catFood, catClothes, catItems, catToys;
    private EditText etTitle, etDescription, etQuantity, etExpiry, etLocation;
    private Button btnPostDonation;
    private String selectedCategory = "Food"; // Default
    private double selectedLat = 0, selectedLng = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_donate);


        initViews();
        setupListeners();
    }

    private void initViews() {
        uploadZone = findViewById(R.id.uploadZone);
        ivPreview = findViewById(R.id.ivPreview);
        
        catFood = findViewById(R.id.catFood);
        catClothes = findViewById(R.id.catClothes);
        catItems = findViewById(R.id.catItems);
        catToys = findViewById(R.id.catToys);
        
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etQuantity = findViewById(R.id.etQuantity);
        etExpiry = findViewById(R.id.etExpiry);
        etLocation = findViewById(R.id.etLocation);
        
        btnPostDonation = findViewById(R.id.btnPostDonation);
    }

    private void setupListeners() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        uploadZone.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                openCamera();
            } else {
                requestCameraPermission();
            }
        });

        // Category Listeners
        catFood.setOnClickListener(v -> selectCategory("Food", catFood));
        catClothes.setOnClickListener(v -> selectCategory("Clothes", catClothes));
        catItems.setOnClickListener(v -> selectCategory("Items", catItems));
        catToys.setOnClickListener(v -> selectCategory("Toys", catToys));

        // Expiry Date Picker
        etExpiry.setOnClickListener(v -> showDatePicker());

        // Map Location Picker
        etLocation.setOnClickListener(v -> {
            Intent intent = new Intent(DonateActivity.this, MapPickerActivity.class);
            startActivityForResult(intent, MAP_PICKER_REQUEST_CODE);
        });

        // Post Donation
        btnPostDonation.setOnClickListener(v -> handlePostDonation());
    }

    private void selectCategory(String category, TextView selectedView) {
        selectedCategory = category;
        
        // Reset all categories to default style
        catFood.setBackgroundResource(R.drawable.bg_chip);
        catFood.setTextColor(getResources().getColor(R.color.text_secondary));
        
        catClothes.setBackgroundResource(R.drawable.bg_chip);
        catClothes.setTextColor(getResources().getColor(R.color.text_secondary));
        
        catItems.setBackgroundResource(R.drawable.bg_chip);
        catItems.setTextColor(getResources().getColor(R.color.text_secondary));
        
        catToys.setBackgroundResource(R.drawable.bg_chip);
        catToys.setTextColor(getResources().getColor(R.color.text_secondary));

        // Apply active style to selected
        selectedView.setBackgroundResource(R.drawable.bg_chip_active);
        selectedView.setTextColor(getResources().getColor(R.color.white));
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year1;
                    etExpiry.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void handlePostDonation() {
        String title = etTitle.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String qty = etQuantity.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();
        String loc = etLocation.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || qty.isEmpty() || loc.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create donation object
        String timestamp = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(new Date());
        Donation newDonation = new Donation(title, desc, qty, expiry, loc, selectedLat, selectedLng, selectedCategory, currentPhotoPath, timestamp);
        
        // Save donation
        DonationManager.addDonation(newDonation);

        String message = "Success! Your donation of '" + title + "' has been posted.";
        
        new AlertDialog.Builder(this)
                .setTitle("Donation Posted")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    setResult(RESULT_OK);
                    finish();
                })
                .show();
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show();
            }
            
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getApplicationContext().getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            if (ivPreview != null) {
                ivPreview.setVisibility(View.VISIBLE);
                ivPreview.setImageURI(Uri.fromFile(new File(currentPhotoPath)));
                findViewById(R.id.tvCameraEmoji).setVisibility(View.GONE);
                findViewById(R.id.tvAddPhotos).setVisibility(View.GONE);
                findViewById(R.id.tvUploadHint).setVisibility(View.GONE);
            }
            Toast.makeText(this, "Photo added!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == MAP_PICKER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String address = data.getStringExtra("address");
            selectedLat = data.getDoubleExtra("latitude", 0);
            selectedLng = data.getDoubleExtra("longitude", 0);
            etLocation.setText(address);
        }

    }
}

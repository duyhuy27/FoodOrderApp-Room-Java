package huyndph30375.fpoly.foodorder3.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import huyndph30375.fpoly.foodorder3.R;
import huyndph30375.fpoly.foodorder3.adapter.AddressAdapter;
import huyndph30375.fpoly.foodorder3.adapter.FoodSummaryAdapter;
import huyndph30375.fpoly.foodorder3.constant.Constant;
import huyndph30375.fpoly.foodorder3.constant.GlobalFunction;
import huyndph30375.fpoly.foodorder3.constant.SpaceItemDecorationCustomLiner;
import huyndph30375.fpoly.foodorder3.databinding.ActivityFinishOrderBinding;
import huyndph30375.fpoly.foodorder3.model.Address;
import huyndph30375.fpoly.foodorder3.model.FoodSummary;
import huyndph30375.fpoly.foodorder3.model.OrderInformation;
import huyndph30375.fpoly.foodorder3.room.FoodDatabase;

public class FinishOrderActivity extends AppCompatActivity implements AddressAdapter.IAddress, FoodSummaryAdapter.ISummary {
    private ActivityFinishOrderBinding binding;
    private Dialog dialog;
    private Dialog dialogUpdate;

    private List<Address> addressList;
    private Address addressObject;
    private AddressAdapter addressAdapter;

    private FoodSummary foodSummary;
    private List<FoodSummary> foodSummaryList;
    private FoodSummaryAdapter foodSummaryAdapter;

    private List<OrderInformation> orderInformationList;
    private OrderInformation orderInformation;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinishOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addressList = FoodDatabase.getInstance(getApplicationContext()).daoAddress().getDataAddress();
        addressAdapter = new AddressAdapter(this);
        addressObject = new Address();

        foodSummary = new FoodSummary();
        foodSummaryList = FoodDatabase.getInstance(getApplicationContext()).daoSummary().getSummaryList();
        foodSummaryAdapter = new FoodSummaryAdapter(this);

        orderInformationList = new ArrayList<>();
        orderInformation = new OrderInformation();

        getDataIntent();

        initRecyclerviewAddress();
        loadDataAddress();

        initRecyclerviewFoodSummary();
        loadDataFoodSummary();

        foodSummaryList = FoodDatabase.getInstance(getApplicationContext()).daoSummary().getSummaryList();
        addressList = FoodDatabase.getInstance(getApplicationContext()).daoAddress().getDataAddress();
        paymentMethod();
        listener();
    }

    private void initRecyclerviewFoodSummary() {
        binding.recyclerviewDetails.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewDetails.setAdapter(foodSummaryAdapter);
        int space = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp);
        SpaceItemDecorationCustomLiner itemDecoration = new SpaceItemDecorationCustomLiner(space);
        binding.recyclerviewAddress.addItemDecoration(itemDecoration);
    }

    private void loadDataFoodSummary() {
        foodSummaryList = FoodDatabase.getInstance(getApplicationContext()).daoSummary().getSummaryList();
        foodSummaryAdapter.setData(foodSummaryList);
    }

    private void initRecyclerviewAddress() {
        binding.recyclerviewAddress.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewAddress.setAdapter(addressAdapter);
        loadDataAddress();
        int space = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp);
        SpaceItemDecorationCustomLiner itemDecoration = new SpaceItemDecorationCustomLiner(space);
        binding.recyclerviewAddress.addItemDecoration(itemDecoration);
    }

    private void loadDataAddress() {
        addressList = FoodDatabase.getInstance(getApplicationContext()).daoAddress().getDataAddress();
        addressAdapter.setData(addressList);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void listener() {
        binding.AddDc.setOnClickListener(view -> {
            Opendialog();
            EditText name = dialog.findViewById(R.id.inputName);
            EditText address = dialog.findViewById(R.id.inputAddress);
            EditText details = dialog.findViewById(R.id.inputDetails);
            EditText note = dialog.findViewById(R.id.inputNote);
            Button addAddress = dialog.findViewById(R.id.addAddress);
            ImageView dismissDialog = dialog.findViewById(R.id.dismissDialog);

            dismissDialog.setOnClickListener(view12 -> dialog.dismiss());

            addAddress.setOnClickListener(view1 -> {
                String nameStr = name.getText().toString();
                String addressStr = address.getText().toString();
                String detailsStr = details.getText().toString();
                String noteStr = note.getText().toString();

                if (nameStr.isEmpty() || addressStr.isEmpty() || detailsStr.isEmpty() || noteStr.isEmpty()) {
                    name.setError("Not empty");
                    address.setError("Not empty");
                    details.setError("Not empty");
                    note.setError("Not empty");
                } else {
                    addressObject = new Address(nameStr, addressStr, detailsStr, noteStr);
                    FoodDatabase.getInstance(getApplicationContext()).daoAddress().InsertAddress(addressObject);
                    Snackbar.make(view, "Add Address Successfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    loadDataAddress();
                    initRecyclerviewAddress();
                    dialog.dismiss();
                }
            });
        });

        binding.back.setOnClickListener(view -> onBackPressed());
        totalPriceToPay();

        binding.pay.setOnClickListener(view -> {
            if (orderInformation.getAddress() == null) {
                Toast.makeText(this, "Please add address", Toast.LENGTH_SHORT).show();
            } else if (orderInformation.getPayment() == null) {
                Toast.makeText(this, "Please add the payment method", Toast.LENGTH_SHORT).show();
            } else {
                loadDataFoodSummary();
                OrderInformation newOrder = new OrderInformation(orderInformation.getNameSummary(), orderInformation.getAddress(), orderInformation.getPayment(), orderInformation.getTotalPrice(), orderInformation.getQuantity());
                orderInformationList.add(newOrder);
                FoodDatabase.getInstance(getApplicationContext()).daoOrder().insertOrder(newOrder);
                ProgressDialog progressDialog = new ProgressDialog(this);
                FoodDatabase.getInstance(getApplicationContext()).daoSummary().deleteAllSummaries();
                foodSummaryAdapter.notifyDataSetChanged();
                loadDataFoodSummary();
                binding.totalPrice.setText("0 000");
                progressDialog.setMessage("Loading..." + "\n Confirming your application !");
                progressDialog.setCancelable(false);
                progressDialog.show();

                new Handler().postDelayed(() -> {
                    progressDialog.dismiss();

                    showDialogConfirm(R.layout.dialog_finish_order);
                }, 2000);

            }
        });

    }

    private void showDialogConfirm(int layout) {
        dialogBuilder = new AlertDialog.Builder(FinishOrderActivity.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.button);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        dialogButton.setOnClickListener(view -> startActivity(new Intent(FinishOrderActivity.this, MainActivity.class)));
    }

    private void Opendialog() {
        dialog = new Dialog(FinishOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bottom_order);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public int getItemSize() {
        return addressList.size();
    }

    @Override
    public Address getDataAddress(int position) {
        return addressList.get(position);
    }

    @Override
    public void clickToEdit(int position) {
        addressObject = addressList.get(position);
        loadDataAddress();
        OpendialogUpdate();
        EditText name = dialogUpdate.findViewById(R.id.inputName);
        EditText address = dialogUpdate.findViewById(R.id.inputAddress);
        EditText details = dialogUpdate.findViewById(R.id.inputDetails);
        EditText note = dialogUpdate.findViewById(R.id.inputNote);
        AppCompatButton update = dialogUpdate.findViewById(R.id.update);
        AppCompatButton cancel = dialogUpdate.findViewById(R.id.dismisUpdate);

        name.setText(addressList.get(position).getName());
        address.setText(addressList.get(position).getAddress());
        details.setText(addressList.get(position).getDetailsAddress());
        note.setText(addressList.get(position).getNoteToDriver());

        cancel.setOnClickListener(view -> dialogUpdate.dismiss());
        update.setOnClickListener(view -> {
            String nameStr = name.getText().toString();
            String addressStr = address.getText().toString();
            String detailsStr = details.getText().toString();
            String noteStr = note.getText().toString();

            if (nameStr.isEmpty() || addressStr.isEmpty() || detailsStr.isEmpty() || noteStr.isEmpty()) {
                name.setError("Not empty");
                address.setError("Not empty");
                details.setError("Not empty");
                note.setError("Not empty");
            } else {
                Address updateAddress = new Address(nameStr, addressStr, detailsStr, noteStr);
                updateAddress.setIdAddress(addressObject.getIdAddress());
                FoodDatabase.getInstance(getApplicationContext()).daoAddress().UpdateAddress(updateAddress);
                Snackbar.make(view, "Add Address Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addressList = FoodDatabase.getInstance(getApplicationContext()).daoAddress().getDataAddress();
                loadDataAddress();
                initRecyclerviewAddress();
                dialogUpdate.dismiss();
            }

        });
    }

    @Override
    public void clickChooseAddress(View v, int position) {
        String address;
        addressObject = addressList.get(position);
        address = addressList.get(position).getDetailsAddress();
        orderInformation.setAddress(address);
    }


    private void OpendialogUpdate() {
        dialogUpdate = new Dialog(FinishOrderActivity.this);
        dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdate.setContentView(R.layout.dialog_bottom_order_update);
        Window window = dialogUpdate.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        dialogUpdate.setCancelable(true);
        dialogUpdate.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        loadDataAddress();
        addressAdapter.notifyDataSetChanged();
        loadDataFoodSummary();
        foodSummaryAdapter.notifyDataSetChanged();
    }

    @Override
    public int countSummary() {
        return foodSummaryList.size();
    }

    @Override
    public FoodSummary getDataSummary(int position) {
        return foodSummaryList.get(position);
    }

    @Override
    public void ClickDelete(int position) {
        foodSummary = foodSummaryList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirm")
                .setMessage("Are you sure to delete " + foodSummaryList.get(position).getGetNameFood())
                .setPositiveButton("Delete", (dialog, which) -> {
                    FoodDatabase.getInstance(getApplicationContext()).daoSummary().deleteSummary(foodSummary);
                    foodSummaryList.remove(position);
                    foodSummaryAdapter.notifyItemRemoved(position);
                    totalPriceToPay();
                })
                .setNegativeButton("Cancel", (dialog, which) ->
                        dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void totalPriceToPay() {
        loadDataFoodSummary();
        int totalPrice = 0;
        for (FoodSummary f : foodSummaryList) {
            totalPrice += f.getPrice();
        }
        binding.totalPrice.setText(totalPrice + Constant.CURRENCY);
        orderInformation.setTotalPrice(totalPrice);
    }

    @SuppressLint("NonConstantResourceId")
    private void paymentMethod() {
        binding.paymentMethodRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            String selectedPaymentMethod = "";
            switch (checkedId) {
                case R.id.payment_method_cash:
//                    selectedPaymentMethod = binding.paymentMethodCash.getText().toString();
                    selectedPaymentMethod = "Cash";
                    break;
                case R.id.payment_method_transfer:
//                    selectedPaymentMethod = binding.paymentMethodTransfer.getText().toString();
                    selectedPaymentMethod = "Transfer";
                    break;
                case R.id.payment_method_atm:
//                    selectedPaymentMethod = binding.paymentMethodAtm.getText().toString();
                    selectedPaymentMethod = "ATM";
                    break;
            }

            if (!selectedPaymentMethod.isEmpty()) {
                orderInformation.setPayment(selectedPaymentMethod);
            } else {
                Toast.makeText(getApplicationContext(), "Please select a payment method", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            foodSummary = (FoodSummary) bundle.get("foodSummary");
            orderInformation.setNameSummary(foodSummary.getGetNameFood());
            String name = foodSummary.getGetNameFood();
            Log.d("getDataIntent", "getDataIntent: " + name);

        }
    }

}
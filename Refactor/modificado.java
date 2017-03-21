package com.grability.core.newworld.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.grability.base.managers.preferences.ManagerPreferences;
import com.grability.base.utils.LogUtil;
import com.grability.core.newworld.R;
import com.grability.core.newworld.controllers.ProductsController;
import com.grability.core.newworld.databinding.ActivityProductDetailBinding;
import com.grability.core.newworld.interfaces.DialogFragmentView;
import com.grability.core.newworld.managers.persistence.models.NewWorldProduct;
import com.grability.core.newworld.managers.persistence.preferences.NewWorldPreferencesKey;
import com.grability.core.newworld.managers.persistence.queries.detailProduct.LoadDetailProducts;
import com.grability.core.newworld.ui.adapters.ProductDetailAdapter;
import com.grability.core.newworld.ui.fragments.dialogFragment.AccountDialogFragment;
import com.grability.core.newworld.ui.fragments.productdetail.AddToListFragment;
import com.grability.core.newworld.ui.fragments.productdetail.NewListFragment;
import com.grability.core.newworld.ui.viewmodels.MainViewModel;
import com.grability.core.newworld.utils.ArrayUtil;
import com.grability.core.newworld.utils.ViewPosition;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ProductDetailActivity extends BaseFragmentActivity implements 
		ViewPager.OnPageChangeListener, NewListFragment.createListFragmentListener, 
		AddToListFragment.AddToListFragmentListener {

	public static final String TAG = ProductDetailActivity.class.getName();
	public static final String MULTIPLE_DETAIL = "MULTIPLE_DETAIL";
	public static final String MODEL_PRODUCT = "MODEL_PRODUCT";
	public static final String QUANTITY_PRODUCT = "QUANTITY_PRODUCT";
	public static final String PRODUCT_KEY = "PRODUCT_KEY";
	public static final String POSITION = "POSITION";
	public static final String FROM_LIST = "FROM_LIST";
	public static final int MAX_DESCRIPTION_LINES = 5;
	public static final int LIST_REQUEST = 0;
	private static final int BASCULE_REQUEST = 1;
	private static final int PRODUCT_TUTORIAL_REQUEST = 2;
	public static final int COMMENT_FRAGMENT_REQUEST = 3;
	private ActivityProductDetailBinding binding;
	private AddToListFragment addToListFragment;
	private ProductDetailAdapter productsPagerAdapter;
	private ViewPager viewPagerDetailProduct;
	private NewWorldProduct newWorldProduct;
	private int previousState;
	private int pagerPosition;
	private boolean isMultipleDetail;
	private boolean storeDetailProduct = false;

	@Inject
	ProductsController productsController;
	@Inject
	MainViewModel mainViewModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getComponent().inject(this);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
		startTutorial();
	}

	private void startTutorial() {
		if(!ManagerPreferences.get().getBoolean(NewWorldPreferencesKey.DETAIL_PRODUCT_TUTORIAL)) {
			Intent intent = new Intent(this, TutorialActivity.class);
			intent.putExtra(TutorialActivity.TUTORIALS, TutorialActivity.DETAIL_PRODUCT_TUTORIAL);
			startActivityForResult(intent, PRODUCT_TUTORIAL_REQUEST);
		} else {
			initProducts();
		}
	}

	private void initProducts() {
		newWorldProduct = getIntent().getExtras().getParcelable(MODEL_PRODUCT);
		isMultipleDetail = getIntent().getExtras().getBoolean(MULTIPLE_DETAIL);
		manageDetailProductBehavior(newWorldProduct, 30, false);
	}

	private void manageDetailProductBehavior(NewWorldProduct newWorldProduct, int
			productsToSearch, boolean isUpdateView) {
		if (isMultipleDetail && (mainViewModel.isInitSeekerProductDetail() || 
				mainViewModel.isInitTrolleyProductDetail())) {
			launchMultipleProductDetail(isUpdateView);
		} else if (isMultipleDetail) {
			storeDetailProduct = true;
			launchProductDetailFromStore(newWorldProduct, productsToSearch, isUpdateView);
		} else {
			launchSingleProductDetail(isUpdateView);
		}
	}

	private void launchMultipleProductDetail(boolean isUpdateView) {
		if (ArrayUtil.collectionIsNotEmpty(mainViewModel.getCarouselDetailProducts())) {
			List<NewWorldProduct> newWorldProducts = mainViewModel.getCarouselDetailProducts();
			updateView(new ArrayList<>(newWorldProducts), isUpdateView);
		}
	}

	private void launchProductDetailFromStore(NewWorldProduct newWorldProduct, int
			productsToSearch, boolean isUpdateView) {
		new LoadDetailProducts(mainViewModel.getGlobalIndexes(String.valueOf(newWorldProduct.getLinearProductIndex()), productsToSearch)).execute(
				newWorldProducts -> {

			if (newWorldProducts != null && !newWorldProducts.isEmpty()) {
				mainViewModel.setCurrentLinearIndex(newWorldProducts);
				updateView(newWorldProducts, isUpdateView);
			}
		});
	}
	
	private void launchSingleProductDetail(boolean isUpdateView) {
		ArrayList<NewWorldProduct> newWorldProducts = new ArrayList<>();
		newWorldProducts.add(newWorldProduct);
		updateView(newWorldProducts, isUpdateView);
	}
	
	private void updateView(ArrayList<NewWorldProduct> newWorldProducts, boolean isUpdateView) {
		if (isUpdateView) {
			addProducts(newWorldProducts);
		} else {
			setupDetailFragments(newWorldProducts);
		}
	}
	
	private void addProducts(ArrayList<NewWorldProduct> newWorldProducts) {
		for (NewWorldProduct newWorldProduct : newWorldProducts) {
			productsPagerAdapter.addFragment(newWorldProduct);
		}
		productsPagerAdapter.notifyDataSetChanged();
		LogUtil.e(TAG, "Size adapter: " + productsPagerAdapter.getCount());
	}
	
	private void setupDetailFragments(ArrayList<NewWorldProduct> newWorldProducts) {
		initView(newWorldProducts);
		initListeners();
		buildView(newWorldProducts);
	}
	
	private void initView(ArrayList<NewWorldProduct> newWorldProducts) {
		viewPagerDetailProduct = binding.viewPagerDetailProduct;
		productsPagerAdapter = new ProductDetailAdapter(getSupportFragmentManager(),
				newWorldProducts, getIntent().getExtras().getBoolean(FROM_LIST, false));
		LogUtil.e(TAG, "Size adapter: " + productsPagerAdapter.getCount());
	}
	
	private void initListeners() {
		viewPagerDetailProduct.addOnPageChangeListener(this);
	}
	
	private void buildView(ArrayList<NewWorldProduct> newWorldProducts) {
		viewPagerDetailProduct.setAdapter(productsPagerAdapter);
		viewPagerDetailProduct.setOffscreenPageLimit(3);
		viewPagerDetailProduct.setClipToPadding(false);
		viewPagerDetailProduct.setPageMargin(24);
		viewPagerDetailProduct.setCurrentItem(getPagerPosition(newWorldProducts));
	}
	

	private int getPagerPosition(ArrayList<NewWorldProduct> newWorldProducts) {
		int currentPosition = 0;
		for (int i = 0; i < newWorldProducts.size(); i++) {
			if (newWorldProducts.get(i).getId().equalsIgnoreCase(this.newWorldProduct.getId()) &&
				newWorldProducts.get(i).getLinearProductIndex() == this.newWorldProduct.getLinearProductIndex()) {
				currentPosition = i;
			}
		}
		return currentPosition;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == LIST_REQUEST) {
			onLaunchAddToList(newWorldProduct);

		} else if (resultCode == RESULT_OK && requestCode == BASCULE_REQUEST) {
			Bundle resultInfo = data.getExtras();

			if (resultInfo.getBoolean(WeightActivity.PRODUCT, false)) {
				addToListFragment.close();
				showConfirmation(getString(R.string.add_product_list_added), binding.getRoot());
			}

		} else if (requestCode == PRODUCT_TUTORIAL_REQUEST) {
			ManagerPreferences.get().set(NewWorldPreferencesKey.DETAIL_PRODUCT_TUTORIAL, true);
			initProducts();
		
		} else if (resultCode == RESULT_OK && requestCode == COMMENT_FRAGMENT_REQUEST) {
			productsPagerAdapter.getCurrentFragment().addCommentFragment();
		}

	}

	public void onLaunchAddToList(NewWorldProduct newWorldProduct) {
		if (newWorldProduct.getPrice() != null) {
			this.newWorldProduct = newWorldProduct;
			addToListFragment = AddToListFragment.newInstance(newWorldProduct);
			addToListFragment.setFormDetailProduct(true);
			addFragment(addToListFragment.animate());
		}
	}

	public void onLaunchAccountActivity(View view, int requestCode, NewWorldProduct newWorldProduct) {
		int[] locations = new int[2];
		view.getLocationOnScreen(locations);
		ViewPosition viewPosition = new ViewPosition(locations, view.getWidth(), view.getHeight());
		Intent intent = new Intent(this, AccountActivity.class);
		intent.putExtra(POSITION, viewPosition);
		startActivityForResult(intent, requestCode);
	}

	public void onCloseActivity(boolean productAddedToBasket, NewWorldProduct newWorldProduct) {
		Intent intent = new Intent();
		intent.putExtra(MainActivity.CURRENT_LINEAR_ID, storeDetailProduct ? newWorldProduct.getLinearId() : newWorldProduct.getLinearIndex());
		
		if (productAddedToBasket) {
			setResult(MainActivity.RESULT_CODE_PRODUCT_ADDED, intent);
		} else {
			setResult(RESULT_OK, intent);
		}
		
		finish();
	}
	
	@Override
	public void onPageSelected(int position) {
		LogUtil.e(TAG, "Scroll!! " + productsPagerAdapter.getCount() + " position " + position);
		pagerPosition = position;
	}
	
	@Override
	public void onPageScrollStateChanged(int state) {
		if (storeDetailProduct) {
			int lastPage = productsPagerAdapter.getCount() - 1;
			
			if (previousState == ViewPager.SCROLL_STATE_SETTLING && state == ViewPager.SCROLL_STATE_IDLE) {
				if (pagerPosition == (lastPage - 1)) {
					goToProduct(productsPagerAdapter.getNewWorldProduct(pagerPosition - 2));
				}
			}
			
			previousState = state;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
	}
	
	@Override
	public void onCreateListClicked() {
		NewListFragment fragment = NewListFragment.newInstance(this.newWorldProduct);
		fragment.setListListener(this);
		addFragment(fragment.animate());
	}
	
	@Override
	public void basculeToList(NewWorldProduct newWorldProduct, String id, String name) {
		Intent intent = getBasculeIntent(newWorldProduct);
		intent.putExtra(WeightActivity.ADD_TO_LIST, true);
		intent.putExtra(WeightActivity.LIST_ADD_ID, id);
		intent.putExtra(WeightActivity.LIST_ADD_NAME, name);
		startActivityForResult(intent, BASCULE_REQUEST);
	}
	
	@Override
	public void basculeToList(NewWorldProduct newWorldProduct, String name) {
		basculeToList(newWorldProduct, "", name);
	}

	public Intent getBasculeIntent(NewWorldProduct newWorldProduct) {
		Intent intent = new Intent(ProductDetailActivity.this, WeightActivity.class);
		intent.putExtra(WeightActivity.PRODUCT, newWorldProduct);
		return intent;
	}

	public void onShowSnackBar() {
		onNewDialog(new AccountDialogFragment());
	}

	private void onNewDialog(DialogFragmentView fragment) {
		showFragmentDialog(fragment);
	}
	
	private void goToProduct(NewWorldProduct newWorldProduct) {
		onSwipeAtRight(newWorldProduct);
	}

	public void onSwipeAtRight(NewWorldProduct newWorldProduct) {
		LogUtil.e(TAG, "productId: " + newWorldProduct);
		manageDetailProductBehavior(newWorldProduct, 1, true);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

}
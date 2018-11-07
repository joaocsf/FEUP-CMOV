package feup.cmpv.feup.casadamusica.fragments.bar;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.ViewPagerAdapter;
import feup.cmpv.feup.casadamusica.fragments.TabFragment;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.view.MainBody;

public class BarTabFragment extends TabFragment implements ViewPager.OnPageChangeListener {

    BarProductsFragment barProducts;

    public static Fragment getInstance(){
        Fragment fragment = new BarTabFragment();
        Bundle b = new Bundle();
        b.putInt("color", R.color.bottomtab_2);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected void setupViewPagerAdapter(ViewPagerAdapter pvadapter) {
        barProducts = (BarProductsFragment)BarProductsFragment.getInstance();
        pvadapter.addFragment(
                barProducts, "Bar");
        pvadapter.addFragment(
                BarProductsFragment.getInstance(), "Purchases");

        getViewPager().addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private int fetchColor(@ColorRes int color){
        return ContextCompat.getColor(getContext(), color);
    }

    @Override
    public void onSelected() {
        onPageSelected(getViewPager().getCurrentItem());
    }

    @Override
    public void onDeselected() {
        onPageSelected(-1);
    }

    public  void generatePurchase(){
        ArrayList<Product> products = barProducts.getPurchase();

        BarPurchaseConfirmFragment confirmPurchase = BarPurchaseConfirmFragment.getInstance(products);
        confirmPurchase.show(getChildFragmentManager(), "Confirm Purchase");
    }

    @Override
    public void onPageSelected(int position) {
        FloatingActionButton floatingButton = ((MainBody)getActivity()).getFloatingActionButton();
        switch (position) {
            case 0:
                floatingButton.setBackgroundTintList(ColorStateList.valueOf(fetchColor(R.color.bottomtab_2)));
                floatingButton.setRippleColor(fetchColor(R.color.white));
                floatingButton.setImageResource(R.drawable.ic_payment_black_24dp);
                floatingButton.setOnClickListener((param)-> generatePurchase());
                barProducts.checkPurchase(floatingButton);
                Log.d("Fab", "True");
                break;
            default:
                floatingButton.hide();
                Log.d("Fab", "False");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

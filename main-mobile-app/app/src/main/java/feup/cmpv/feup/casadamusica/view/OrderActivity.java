package feup.cmpv.feup.casadamusica.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.TicketListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.VoucherListAdapter;
import feup.cmpv.feup.casadamusica.structures.Order;

import static feup.cmpv.feup.casadamusica.utils.Utils.df2;
import static feup.cmpv.feup.casadamusica.utils.Utils.formatDate;

public class OrderActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.order_recipt_activity);
        Bundle extras = getIntent().getExtras();
        Order order = (Order)extras.getSerializable("order");
        order.parseEverything();

        TextView orderNumber = findViewById(R.id.order_recipt_number);
        TextView orderDate = findViewById(R.id.order_recipt_date);
        TextView orderHour = findViewById(R.id.order_recipt_hour);
        TextView total = findViewById(R.id.order_recipt_total_value);
        TextView orderTitle = findViewById(R.id.order_recipt_title);
        TextView nif = findViewById(R.id.order_recipt_nif);

        LinearLayout productsLayout = findViewById(R.id.order_recipt_products);
        ListView productsList = findViewById(R.id.order_recipt_products_list);

        LinearLayout ticketsLayout = findViewById(R.id.order_recipt_tickets);
        ListView ticketsList = findViewById(R.id.order_recipt_tickets_list);

        LinearLayout vouchersLayout = findViewById(R.id.order_recipt_vouchers);
        ListView vouchersList = findViewById(R.id.order_recipt_vouchers_list);

        orderNumber.setText("#"+order.getId());
        String[] date = formatDate(order.getDate()).split(" ");
        orderDate.setText(date[0]);
        orderHour.setText(date[1]);
        total.setText(df2.format(order.getTotal()) + "€");
        orderTitle.setText(order.getType().equals("product")? "Bar Order": "Ticket Order");
        nif.setText(order.getNif().replaceAll("(.{3})", "$1 "));

        if(order.getProducts().size() <= 0){
            productsLayout.setVisibility(View.GONE);
        }else{
            ProductListAdapter productListAdapter = new ProductListAdapter(this, order.getProducts(), R.layout.purchase_product_list_item, false);
            productsList.setAdapter(productListAdapter);
            productsList.computeScroll();
            setDynamicHeight(productsList);
        }

        if(order.getVoucherGroups().size() <= 0){
            vouchersLayout.setVisibility(View.GONE);
        }else{
            VoucherListAdapter voucherListAdapter = new VoucherListAdapter(this, order.getVoucherGroups(), R.layout.voucher_simple_list_item);
            vouchersList.setAdapter(voucherListAdapter);
            vouchersList.computeScroll();
            setDynamicHeight(vouchersList);
        }

        if(order.getShowTickets().size() <= 0){
            ticketsLayout.setVisibility(View.GONE);
        }else{
            TicketListAdapter ticketListAdapter = new TicketListAdapter(this, order.getShowTickets(), R.layout.ticket_simple_list_item);
            ticketsList.setAdapter(ticketListAdapter);
            ticketsList.computeScroll();
            setDynamicHeight(ticketsList);
        }
    }

    public static void setDynamicHeight(ListView mListView) {
        ListAdapter mListAdapter = mListView.getAdapter();
        if (mListAdapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }
}

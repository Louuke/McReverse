package icu.jnet.mcd.api.request;

public class CartRequest implements Request {

    private int operationMode = 1, validationType = 0;
    private String[] options = { "ApplyPromotion", "CalculateEnergy", "EnableAutoEVM" };
    private OrderView orderView = new OrderView();

    private static class OrderView {

        private int fulfillmentType = 1, priceType = 1;
        private boolean isTpOrder = false;
        private Object[] products = {};
        private final PromotionListView[] promotionListView = { new PromotionListView() };
        private String storeId = "27601024";
    }

    private static class PromotionListView {

        private int id = -13548, type = 2;
        private ProductSet[] productSets = { new ProductSet() };
    }

    private static class ProductSet {

        private int action = 0;
        private String alias = "Item to discount";
        private Product[] products = { new Product() };
    }

    private static class Product {

        private Object[] choices = {}, components = {}, customizations = {};
        private int deliverEarlyQuantity = 0, productCode = 3007, promoQuantity = 0, quantity = 1;
        private boolean isAutoEVM = false;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/cart";
    }
}

package online.kenya.mvitu.controller;


import online.kenya.mvitu.constants.FirebaseCollections;
import online.kenya.mvitu.constants.FirebaseFields;
import online.kenya.mvitu.constants.FirebaseInit;
import online.kenya.mvitu.interfaces.getItemsCallback;
import online.kenya.mvitu.models.Categories;
import online.kenya.mvitu.models.GoodType;
import online.kenya.mvitu.models.Goods;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class StoreHelper {

    public static void getAllGoods(getItemsCallback callback) {
        FirebaseCollections.STORE_REFERENCE.limit(4).get().addOnSuccessListener(snapshot -> {
            List<Goods> goods=new ArrayList<>();
            for (DocumentSnapshot snapshotDocs:snapshot){
                Goods good=snapshotDocs.toObject(Goods.class);
                good.setGoodId(snapshotDocs.getId());
                good.setGoodName(snapshotDocs.getString(FirebaseFields.GOOD_NAME));
                good.setGoodDescription(snapshotDocs.getString(FirebaseFields.GOOD_DESCRIPTION));
                good.setCategory(snapshotDocs.getString(FirebaseFields.GOOD_CATEGORY));
                goods.add(good);
            }
            callback.onSuccess(goods);
        }).addOnFailureListener(e -> {
                    callback.onError();
                });
    }
    public static void getAllGoodsInCategory(String category,getItemsCallback callback) {
        Query query=FirebaseCollections.STORE_REFERENCE.whereEqualTo(FirebaseFields.GOOD_CATEGORY,category);

        query.get().addOnSuccessListener(snapshot -> {
            List<Goods> goods=new ArrayList<>();
            for (DocumentSnapshot snapshotDocs:snapshot){
                Goods good=snapshotDocs.toObject(Goods.class);
                good.setGoodId(snapshotDocs.getId());
                good.setGoodName(snapshotDocs.getString(FirebaseFields.GOOD_NAME));
                good.setGoodDescription(snapshotDocs.getString(FirebaseFields.GOOD_DESCRIPTION));
                good.setCategory(snapshotDocs.getString(FirebaseFields.GOOD_CATEGORY));
                goods.add(good);
            }
            callback.onSuccess(goods);
        }).addOnFailureListener(e -> {
            callback.onError();
        });
    }
    public static void getCategoryImages(String categoryName,getItemsCallback callback){
        StorageReference pathReference = FirebaseInit
                .reference
                .child("categories/"+categoryName+".jpg");

        pathReference.getDownloadUrl().addOnSuccessListener(callback::onSuccess)
                .addOnFailureListener(Throwable::printStackTrace);

    }
    public static void getGoodImages(String goodName,getItemsCallback callback){
        StorageReference pathReference = FirebaseInit
                .reference
                .child("goods/"+goodName+".jpg");

        pathReference.getDownloadUrl().addOnSuccessListener(callback::onSuccess)
                .addOnFailureListener(Throwable::printStackTrace);

    }
    public static void getAllGoodsCategories(getItemsCallback callback) {
        FirebaseCollections.CATEGORY_REFERENCE.get().addOnSuccessListener(snapshot -> {
            List<Categories> categories=new ArrayList<>();
            for (DocumentSnapshot snapshotDocs:snapshot){
                Categories category=new Categories();
                category.setID(snapshotDocs.getId());
                category.setName(snapshotDocs.getString(FirebaseFields.GOOD_CATEGORY));

                categories.add(category);
            }
            callback.onSuccess(categories);
        }).addOnFailureListener(e -> {
            callback.onError();
        });
    }


    public static void getGoodDescription(Goods goods,getItemsCallback callback){
        FirebaseCollections.STORE_REFERENCE.document(goods.getGoodId())
                .collection("good_type")
                .get()
                .addOnSuccessListener(snapshot -> {
            List<GoodType> good_types=new ArrayList<>();

            for (DocumentSnapshot snapshotDocs:snapshot){

                GoodType goodType=new GoodType(goods.getGoodName());

                goodType.setGoodDescription(goods.getGoodDescription());
                goodType.setGoodName(goods.getGoodName());
                goodType.setGoodId(goods.getGoodId());
                goodType.setGoodTypeId(snapshotDocs.getId());
                goodType.setGoodVariantName(snapshotDocs.getString(FirebaseFields.GOOD_VARIANT_NAME));
                goodType.setGoodVariantDescription(snapshotDocs.getString(FirebaseFields.GOOD_VARIANT_DESCRIPTION));

                goodType.setGoodRetailPrice(snapshotDocs.getDouble(FirebaseFields.RETAIL_PRICE));
                goodType.setWholesaleQuantities(snapshotDocs.getDouble(FirebaseFields.WHOLESALE_QUANTITIES));
                goodType.setGoodWholesalePrice(snapshotDocs.getDouble(FirebaseFields.WHOLESALE_PRICE));
               good_types.add(goodType);

            }
            callback.onSuccess(good_types);
        }).addOnFailureListener(e -> {
            callback.onError();
        });
    }

}

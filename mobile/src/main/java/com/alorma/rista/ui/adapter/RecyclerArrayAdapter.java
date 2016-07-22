
package com.alorma.rista.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RecyclerArrayAdapter<ItemType, ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> {

  public List<ItemType> items;
  private LayoutInflater inflater;
  private RecyclerAdapterContentListener recyclerAdapterContentListener;
  private ItemCallback<ItemType> callback;
  private boolean returnResult;

  public RecyclerArrayAdapter(LayoutInflater inflater) {
    this.inflater = inflater;
    items = new ArrayList<>();
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public ItemType getItem(int position) {
    return items.get(position);
  }

  public void add(ItemType itemType) {
    items.add(itemType);
    notifyDataSetChanged();
  }

  public void addAll(Collection<? extends ItemType> items) {
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  public void clear() {
    items.clear();
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return onCreateViewHolder(getInflater(), parent, viewType);
  }

  protected abstract ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    onBindViewHolder(holder, getItem(position));
    if (recyclerAdapterContentListener != null && position + lazyLoadCount() >= getItemCount()) {
      recyclerAdapterContentListener.loadMoreItems();
    }
  }

  @Override
  public int getItemViewType(int position) {
    return getItemViewType(items.get(position));
  }

  protected int getItemViewType(ItemType itemType) {
    return 0;
  }

  protected abstract void onBindViewHolder(ViewHolder holder, ItemType type);

  protected int lazyLoadCount() {
    return 1;
  }

  public LayoutInflater getInflater() {
    return inflater;
  }

  public void setInflater(LayoutInflater inflater) {
    this.inflater = inflater;
  }

  public void remove(ItemType itemType) {
    items.remove(itemType);
    notifyDataSetChanged();
  }

  public List<ItemType> getItems() {
    return items;
  }

  public void setRecyclerAdapterContentListener(RecyclerAdapterContentListener recyclerAdapterContentListener) {
    this.recyclerAdapterContentListener = recyclerAdapterContentListener;
  }

  public ItemCallback<ItemType> getCallback() {
    if (callback == null) {
      callback = item -> {

      };
    }
    return callback;
  }

  public void setCallback(ItemCallback<ItemType> callback) {
    this.callback = callback;
  }

  public void setReturn(boolean aReturn) {
    this.returnResult = aReturn;
  }

  public boolean isReturnResult() {
    return returnResult;
  }

  public void setReturnResult(boolean returnResult) {
    this.returnResult = returnResult;
  }

  public interface RecyclerAdapterContentListener {
    void loadMoreItems();
  }

  public interface ItemCallback<ItemType> {
    void onItemSelected(ItemType item);
  }
}
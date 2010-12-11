package org.gwtmpv.widgets;

import java.util.Iterator;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class GwtDialogBox extends DialogBox implements IsDialogBox {

  @Override
  public void setWidget(final IsWidget isWidget) {
    setWidget(isWidget.asWidget());
  }

  @Override
  public void add(final IsWidget isWidget) {
    add(isWidget.asWidget());
  }

  @Override
  public boolean remove(final IsWidget isWidget) {
    return remove(isWidget.asWidget());
  }

  @Override
  public Widget asWidget() {
    return this;
  }

  @Override
  public IsStyle getStyle() {
    return getIsElement().getStyle();
  }

  @Override
  public IsElement getIsElement() {
    return new GwtElement(getElement());
  }

  @Override
  public void addAutoHidePartner(IsElement element) {
    addAutoHidePartner(element.asElement());
  }

  @Override
  public Iterator<IsWidget> iteratorIsWidgets() {
    return new GwtIsWidgetIteratorAdaptor(iterator());
  }

  @Override
  public IsWidget getIsWidget() {
    return (IsWidget) getWidget();
  }

}

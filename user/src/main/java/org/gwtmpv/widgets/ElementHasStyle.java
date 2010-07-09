package org.gwtmpv.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;

public class ElementHasStyle implements IsStyle {

  private final Element element;

  public ElementHasStyle(final Element element) {
    this.element = element;
  }

  @Override
  public String getOpacity() {
    return element.getStyle().getOpacity();
  }

  @Override
  public void setOpacity(final double value) {
    element.getStyle().setOpacity(value);
  }

  @Override
  public void setWidth(final double value, final Unit unit) {
    element.getStyle().setWidth(value, unit);
  }

  @Override
  public void setHeight(final double value, final Unit unit) {
    element.getStyle().setHeight(value, unit);
  }

  @Override
  public void setColor(final String value) {
    element.getStyle().setColor(value);
  }

  @Override
  public void setFontWeight(final FontWeight value) {
    element.getStyle().setFontWeight(value);
  }

  @Override
  public void setBackgroundColor(final String value) {
    element.getStyle().setBackgroundColor(value);
  }

  @Override
  public void setLeft(final double value, final Unit unit) {
    element.getStyle().setLeft(value, unit);
  }

  @Override
  public void setTop(final double value, final Unit unit) {
    element.getStyle().setTop(value, unit);
  }

  @Override
  public void setMargin(final double value, final Unit unit) {
    element.getStyle().setMargin(value, unit);
  }

  @Override
  public void setMarginBottom(final double value, final Unit unit) {
    element.getStyle().setMarginBottom(value, unit);
  }

  @Override
  public void setMarginLeft(final double value, final Unit unit) {
    element.getStyle().setMarginLeft(value, unit);
  }

  @Override
  public void setMarginRight(final double value, final Unit unit) {
    element.getStyle().setMarginRight(value, unit);
  }

  @Override
  public void setMarginTop(final double value, final Unit unit) {
    element.getStyle().setMarginTop(value, unit);
  }

  @Override
  public void setOverflow(final Overflow value) {
    element.getStyle().setOverflow(value);
  }

  @Override
  public void setBorderColor(final String value) {
    element.getStyle().setBorderColor(value);
  }

  @Override
  public void setBorderStyle(final BorderStyle value) {
    element.getStyle().setBorderStyle(value);
  }

  @Override
  public void setBorderWidth(final double value, final Unit unit) {
    element.getStyle().setBorderWidth(value, unit);
  }

  @Override
  public String getDisplay() {
    return element.getStyle().getDisplay();
  }

  @Override
  public void setDisplay(final Display value) {
    element.getStyle().setDisplay(value);
  }

  @Override
  public String getProperty(final String name) {
    return element.getStyle().getProperty(name);
  }

  @Override
  public void setProperty(final String name, final String value) {
    element.getStyle().setProperty(name, value);
  }

  @Override
  public void setProperty(final String name, final double value, final Unit unit) {
    element.getStyle().setProperty(name, value, unit);
  }

  @Override
  public void setPropertyPx(final String name, final int value) {
    element.getStyle().setPropertyPx(name, value);
  }

}
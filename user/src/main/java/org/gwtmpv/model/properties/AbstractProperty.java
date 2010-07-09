package org.gwtmpv.model.properties;

import static org.gwtmpv.util.ObjectUtils.eq;

import java.util.ArrayList;

import org.gwtmpv.model.events.PropertyChangedEvent;
import org.gwtmpv.model.events.PropertyChangedEvent.PropertyChangedHandler;
import org.gwtmpv.model.validation.Valid;
import org.gwtmpv.model.validation.events.RuleTriggeredEvent;
import org.gwtmpv.model.validation.events.RuleUntriggeredEvent;
import org.gwtmpv.model.validation.events.RuleTriggeredEvent.RuleTriggeredHandler;
import org.gwtmpv.model.validation.events.RuleUntriggeredEvent.RuleUntriggeredHandler;
import org.gwtmpv.model.validation.rules.Required;
import org.gwtmpv.model.validation.rules.Rule;
import org.gwtmpv.model.values.Value;
import org.gwtmpv.util.Inflector;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/** Provides most of the validation/derived/etc. implementation guts of {@link Property}. */
public abstract class AbstractProperty<P, T extends AbstractProperty<P, T>> implements Property<P> {

  // handlers
  protected final HandlerManager handlers = new HandlerManager(this);
  // other properties that are calculated off of our value
  protected final ArrayList<Property<?>> derived = new ArrayList<Property<?>>();
  // other properties that are validated off of our value
  private final ArrayList<Property<?>> downstream = new ArrayList<Property<?>>();
  // rules that validate against our value and fire against our handlers
  private final ArrayList<Rule> rules = new ArrayList<Rule>();
  // our bound/set/derived value
  private final Value<P> value;
  // snapshot of the value for diff purposes (e.g. derived values)
  protected P lastValue;
  // whether the user has touched this field on the screen yet
  protected boolean touched;
  // whether validate() is currently running
  private boolean alreadyValidating = false;
  // the result of the last validate()
  private Valid valid;
  // go back to untouched if we become valid
  private boolean untouchIfValid;
  // ugh
  private boolean ignoreUpstreamTouch;
  // has fired at least once
  private boolean hasFired;

  public AbstractProperty(final Value<P> value) {
    this.value = value;
    lastValue = get();
  }

  @Override
  public P get() {
    return value.get();
  }

  @Override
  public void set(final P value) {
    this.value.set(value);
    setTouched(true); // even if unchanged, treat this as touching
    reassess();
  }

  @Override
  public void setInitial(final P value) {
    this.value.set(value);
    setTouched(false);
    reassess();
  }

  @Override
  public void reassess() {
    final P newValue = get();
    final boolean changed = !eq(lastValue, newValue);
    lastValue = newValue;
    if (changed || !hasFired) {
      fireChanged(false); // should not always be false
      hasFired = true;
    }

    validate();

    for (final Property<?> other : derived) {
      other.reassess();
    }
  }

  @Override
  public HandlerRegistration addPropertyChangedHandler(final PropertyChangedHandler<P> handler) {
    return handlers.addHandler(PropertyChangedEvent.getType(), handler);
  }

  private void fireChanged(final boolean firstLoad) {
    fireEvent(new PropertyChangedEvent<P>(this, firstLoad));
  }

  /** Track {@code other} as derived on us, so we'll forward changed/changing events to it. */
  public <P1 extends Property<?>> P1 addDerived(final P1 other) {
    derived.add(other);
    return other;
  }

  @Override
  public String toString() {
    return value.getName();
  }

  // fluent method of touch + valid
  public Valid touch() {
    setTouched(true);
    return wasValid();
  }

  @Override
  public void addRule(final Rule rule) {
    rules.add(rule);
  }

  @Override
  public void fireEvent(final GwtEvent<?> event) {
    handlers.fireEvent(event);
  }

  @Override
  public T depends(final Property<?>... upstream) {
    for (final Property<?> other : upstream) {
      other.addDerived(this);
    }
    return getThis();
  }

  @Override
  public Valid validate() {
    if (alreadyValidating) {
      throw new IllegalStateException(this + " validation recursed");
    }
    alreadyValidating = true;
    try {
      Valid allValid = Valid.YES;
      for (final Rule rule : rules) {
        final Valid ruleValid = rule.validate(allValid == Valid.NO);
        // returning DEFER is okay
        if (ruleValid == Valid.NO) {
          allValid = Valid.NO;
        }
      }
      if (allValid == Valid.YES && untouchIfValid) {
        touched = false; // setTouched(false);
      }
      valid = allValid;
      for (final Property<?> other : downstream) {
        other.validate();
      }
      return allValid;
    } finally {
      alreadyValidating = false;
    }
  }

  @Override
  public HandlerRegistration addRuleTriggeredHandler(final RuleTriggeredHandler handler) {
    return handlers.addHandler(RuleTriggeredEvent.getType(), handler);
  }

  @Override
  public HandlerRegistration addRuleUntriggeredHandler(final RuleUntriggeredHandler handler) {
    return handlers.addHandler(RuleUntriggeredEvent.getType(), handler);
  }

  @Override
  public boolean isTouched() {
    return touched;
  }

  @Override
  public void setTouched(final boolean touched) {
    this.touched = touched;
    for (final Property<?> other : derived) {
      if (other.isIgnoreUpstreamTouch()) {
        continue;
      }
      other.setTouched(touched);
    }
    validate();
  }

  @Override
  public void addDownstream(final Property<?> other) {
    downstream.add(other);
  }

  @Override
  public Valid wasValid() {
    return valid;
  }

  public Value<P> getValue() {
    return value;
  }

  public void setUntouchIfValid(final boolean untouchIfValid) {
    this.untouchIfValid = untouchIfValid;
  }

  public boolean isIgnoreUpstreamTouch() {
    return ignoreUpstreamTouch;
  }

  public void setIgnoreUpstreamTouch() {
    ignoreUpstreamTouch = true;
  }

  public T req() {
    new Required(this);
    return getThis();
  }

  public T in(final PropertyGroup group) {
    group.add(this);
    return getThis();
  }

  @Override
  public String getName() {
    return Inflector.humanize(toString());
  }

  @Override
  public void pullInitial() {
    setInitial(get());
  }

  protected abstract T getThis();
}
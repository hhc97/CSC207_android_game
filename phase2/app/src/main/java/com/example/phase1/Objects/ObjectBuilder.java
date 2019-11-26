package com.example.phase1.Objects;

public class ObjectBuilder {
  private int difficulty = 0; // default difficulty
  private int monsterHealth = 1; // default health
  private int monsterWorth = 100; // default point when player get a kill

  public ObjectBuilder(int difficulty) {
    this.difficulty = difficulty;
    if (this.difficulty == 0) {
      this.monsterHealth = 1;
      this.monsterWorth = 100;
    } else if (this.difficulty == 1) {
      this.monsterHealth = 5;
      this.monsterWorth = 300;
    } else if (this.difficulty == 2) {
      this.monsterHealth = 10;
      this.monsterWorth = 500;
    }
  }

  public ObjectBuilder() {//Overloading
    if (this.difficulty == 0) {
      this.monsterHealth = 1;
      this.monsterWorth = 100;
    } else if (this.difficulty == 1) {
      this.monsterHealth = 5;
      this.monsterWorth = 300;
    } else if (this.difficulty == 2) {
      this.monsterHealth = 10;
      this.monsterWorth = 500;
    }
  }

  public Object createObject(String type) {
    if (type.equals("Hero")) {
      return createHero();
    } else if (type.equals("Monster")) {
      return createMonster();
    } else if (type.equals("Obstacle")) {
      return createObstacle();
    } else if (type.equals("Potion")) {
      return createPotion();
    } else if (type.equals("Coin")) {
      return createCoin();
    }
    return null;
  }

  private Object createHero() {
    return new Hero();
  }

  private Object createMonster() {
    Monster monster = new Monster();
    monster.setWorth(this.monsterWorth);
    monster.setHealth(this.monsterHealth);
    return monster;
  }

  private Object createObstacle() {
    return new Obstacle();
  }

  private Object createPotion() {
    return new Potion();
  }

  private Object createCoin() {
    return new Coin();
  }
}

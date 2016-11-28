DROP TABLE IF EXISTS RECIPE;
CREATE TABLE RECIPE(ID INTEGER PRIMARY KEY AUTOINCREMENT);

ALTER TABLE RECIPE add TITLE TEXT;
ALTER TABLE RECIPE add CALORIES INTEGER;
ALTER TABLE RECIPE add PREP_TIME INTEGER;
ALTER TABLE RECIPE add COOK_TIME INTEGER;
ALTER TABLE RECIPE add INGREDIENTS TEXT;
ALTER TABLE RECIPE add STEPS TEXT;
ALTER TABLE RECIPE add CATEGORY INTEGER;
ALTER TABLE RECIPE add TYPE INTEGER;
ALTER TABLE RECIPE add IMAGE_PATH TEXT;


INSERT INTO RECIPE(TITLE, CALORIES, PREP_TIME, COOK_TIME, INGREDIENTS, STEPS, CATEGORY, TYPE, IMAGE_PATH)
VALUES ("Poutine", 2170, 5, 20, "Potato\n\rGravy\n\rCheese Curds", "Heat oil in a deep fryer or deep heavy skillet to 365°F (185°C).\n\rWarm gravy in saucepan or microwave.\n\rPlace the fries into the hot oil, and cook until light brown, about 5 minutes.\n\rRemove to a paper towel lined plate to drain.\n\rPlace the fries on a serving platter, and sprinkle the cheese over them.\n\rLadle gravy over the fries and cheese, and serve immediately.",5,2,"");
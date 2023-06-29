# Radius-assigment
Radius assignment

<img width="382" alt="image" src="https://github.com/Mcmillon-W/Radius-assigment/assets/87062465/672f3e8e-b30e-4550-8555-fec854d152cb"> 
Button disabled
<img width="400" alt="image" src="https://github.com/Mcmillon-W/Radius-assigment/assets/87062465/5c12ed8d-396f-47ca-b98d-73da4906b936"> Button enabled
<img width="391" alt="image" src="https://github.com/Mcmillon-W/Radius-assigment/assets/87062465/48e44451-1a3d-407e-94a5-fbf5e5f3a3f6"> Loading state

UI Layout
- First a title depicting that for each facility_id alteast 1 option must be selected (Propert_type, number of rooms etc.)
- Recyclerview rv listing all the facilities and options for each via Radio group whose icons have been replaced with custom ones that were provided (3x image is used when selected else 2x)
- Refresh button to forcefully make API call ignoring the DB
- Submit button to display a toast message showing the elements user has selected (it gets disabled if part of exclusion list)
- 2nd title to display that

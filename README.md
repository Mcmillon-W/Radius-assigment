<div align="center">
  
# Radius-assigment
  
### Button Disabled
<img width="382" alt="image" src="https://github.com/Mcmillon-W/Radius-assigment/assets/87062465/672f3e8e-b30e-4550-8555-fec854d152cb"> 

### Button enabled
<img width="400" alt="image" src="https://github.com/Mcmillon-W/Radius-assigment/assets/87062465/5c12ed8d-396f-47ca-b98d-73da4906b936">

### Loading state
<img width="391" alt="image" src="https://github.com/Mcmillon-W/Radius-assigment/assets/87062465/48e44451-1a3d-407e-94a5-fbf5e5f3a3f6"> 
</div>

## UI Layout
- First a title depicting that for each facility_id alteast 1 option must be selected (Propert_type, number of rooms etc.)
- Recyclerview rv listing all the facilities and options for each via Radio group whose icons have been replaced with custom ones that were provided (3x image is used when selected else 2x)
- Refresh button to forcefully make API call ignoring the DB
- Submit button to display a toast message showing the elements user has selected (it gets disabled if the selected options are part of exclusion combination or options not selected)
- 2nd title to display that exclusions are listed below
- listings of the exclusions via recyclerview rv1

 **Note:**
 - Our first source of truth is the DB but if the operation takes more than 2 seconds we will make the API call so as not to break UX. Of course if nothing is there in DB we will make the API call
 - Im showing a progressBar when data hasn't been fetched either from DB or from API
 - Have locked the screen orientation to be portrait

### Dependencies
Mainly have imported 2 dependencies other than the normal ones which we get when initializing an Empty Activity from Android Studio.
- Retrofit (for making the API call and callbacks)
- Room (for persistence of data - DB)

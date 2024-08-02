
console.log("jay shiroya");

const base_Url = "http://localhost:8080";

const click = document.getElementById('view_model');

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

const instanceOptions = {
    id: 'view_model',
    override: true
};

const views = new Modal(click,options,instanceOptions);


function openView(){
    views.show();

}

function closeView(){
    views.hide();
}


async function getAllData(id){
    console.log(id);
    try {
        const data = await (await fetch(`${base_Url}/api/contacts/` + id)).json();

        document.querySelector("#contact_image").src = data.contactImage;
        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_names").innerHTML = data.name;
        document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_discription").innerHTML = data.description;
        document.querySelector("#contact_discription").innerHTML = data.description;
        document.querySelector("#contact_discription").innerHTML = data.description;


        const contactFavorite = document.querySelector("#contact_favorite");
        if (data.favorite) {
            contactFavorite.innerHTML =
                "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
        } else {
            contactFavorite.innerHTML = "Not Favorite Contact";
        }


        document.querySelector("#contact_website").href = data.websiteLink;
        document.querySelector("#contact_website").innerHTML = data.websiteLink;
        document.querySelector("#contact_linkedIn").href = data.linkedInLink;
        document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;

        openView();
        console.log(data);

    }catch (error) {
        console.log("error"+error);

    }
}

function deleteData(id){
    Swal.fire({
        title: "Are you sure?",
        text: "You won't to be delete this contact!!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {

            const url = `${base_Url}/user/contacts/delete/` + id;
            window.location.replace(url);

        }
    });
}




